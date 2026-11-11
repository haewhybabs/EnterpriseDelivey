package com.parceldelivery.core.service;

import com.parceldelivery.core.dto.RatingRequestDTO;
import com.parceldelivery.core.dto.RatingResponseDTO;
import com.parceldelivery.core.entity.DeliveryRequest;
import com.parceldelivery.core.entity.DeliveryStatus;
import com.parceldelivery.core.entity.Rating;
import com.parceldelivery.core.exception.BadRequestException;
import com.parceldelivery.core.exception.ResourceNotFoundException;
import com.parceldelivery.core.mapper.RatingMapper;
import com.parceldelivery.core.repository.DeliveryRequestRepository;
import com.parceldelivery.core.repository.RatingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RatingServiceTest {

    @Mock
    private RatingRepository ratingRepository;

    @Mock
    private DeliveryRequestRepository deliveryRequestRepository;

    @Mock
    private RatingMapper ratingMapper;

    @InjectMocks
    private RatingService ratingService;

    private DeliveryRequest deliveredRequest() {
        DeliveryStatus status = new DeliveryStatus();
        status.setName("Delivered");

        DeliveryRequest deliveryRequest = new DeliveryRequest();
        deliveryRequest.setId(1L);
        deliveryRequest.setDeliveryStatus(status);
        return deliveryRequest;
    }

    @Test
    void rateDelivery_succeeds_whenDeliveredAndNotYetRated() {
        // Arrange
        RatingRequestDTO dto = new RatingRequestDTO();
        dto.setDeliveryRequestId(1L);
        dto.setSenderRating(5);

        Rating saved = new Rating();
        saved.setId(1L);
        saved.setSenderRating(5);

        RatingResponseDTO expectedDto = new RatingResponseDTO();
        expectedDto.setId(1L);
        expectedDto.setSenderRating(5);

        when(deliveryRequestRepository.findById(1L)).thenReturn(Optional.of(deliveredRequest()));
        when(ratingRepository.findByDeliveryRequestId(1L)).thenReturn(Optional.empty());
        when(ratingRepository.save(any(Rating.class))).thenReturn(saved);
        when(ratingMapper.toResponseDTO(saved)).thenReturn(expectedDto);

        // Act
        RatingResponseDTO result = ratingService.rateDelivery(dto);

        // Assert
        assertThat(result.getSenderRating()).isEqualTo(5);
    }

    @Test
    void rateDelivery_throwsBadRequest_whenNotYetDelivered() {
        // Arrange
        DeliveryStatus status = new DeliveryStatus();
        status.setName("Processing");
        DeliveryRequest deliveryRequest = new DeliveryRequest();
        deliveryRequest.setDeliveryStatus(status);

        RatingRequestDTO dto = new RatingRequestDTO();
        dto.setDeliveryRequestId(1L);

        when(deliveryRequestRepository.findById(1L)).thenReturn(Optional.of(deliveryRequest));

        // Act + Assert
        assertThrows(BadRequestException.class, () -> ratingService.rateDelivery(dto));
    }

    @Test
    void rateDelivery_throwsBadRequest_whenAlreadyRated() {
        // Arrange
        RatingRequestDTO dto = new RatingRequestDTO();
        dto.setDeliveryRequestId(1L);

        when(deliveryRequestRepository.findById(1L)).thenReturn(Optional.of(deliveredRequest()));
        when(ratingRepository.findByDeliveryRequestId(1L)).thenReturn(Optional.of(new Rating()));

        // Act + Assert
        assertThrows(BadRequestException.class, () -> ratingService.rateDelivery(dto));
    }

    @Test
    void rateDelivery_throwsResourceNotFound_whenDeliveryRequestIsMissing() {
        // Arrange
        RatingRequestDTO dto = new RatingRequestDTO();
        dto.setDeliveryRequestId(99L);

        when(deliveryRequestRepository.findById(99L)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(ResourceNotFoundException.class, () -> ratingService.rateDelivery(dto));
    }
}

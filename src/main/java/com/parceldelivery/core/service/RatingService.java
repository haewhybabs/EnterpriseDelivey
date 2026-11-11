package com.parceldelivery.core.service;

import com.parceldelivery.core.dto.RatingRequestDTO;
import com.parceldelivery.core.dto.RatingResponseDTO;
import com.parceldelivery.core.entity.DeliveryRequest;
import com.parceldelivery.core.entity.Rating;
import com.parceldelivery.core.exception.BadRequestException;
import com.parceldelivery.core.exception.ResourceNotFoundException;
import com.parceldelivery.core.mapper.RatingMapper;
import com.parceldelivery.core.repository.DeliveryRequestRepository;
import com.parceldelivery.core.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RatingService {

    private static final String DELIVERED_STATUS = "Delivered";

    private final RatingRepository ratingRepository;
    private final DeliveryRequestRepository deliveryRequestRepository;
    private final RatingMapper ratingMapper;

    @Transactional
    public RatingResponseDTO rateDelivery(RatingRequestDTO dto) {
        DeliveryRequest deliveryRequest = deliveryRequestRepository.findById(dto.getDeliveryRequestId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Delivery request not found with id: " + dto.getDeliveryRequestId()));

        if (!DELIVERED_STATUS.equals(deliveryRequest.getDeliveryStatus().getName())) {
            throw new BadRequestException("Cannot rate a delivery request that is not yet delivered");
        }

        if (ratingRepository.findByDeliveryRequestId(dto.getDeliveryRequestId()).isPresent()) {
            throw new BadRequestException("This delivery request has already been rated");
        }

        Rating rating = new Rating();
        rating.setDeliveryRequest(deliveryRequest);
        rating.setSenderRating(dto.getSenderRating());

        Rating saved = ratingRepository.save(rating);
        return ratingMapper.toResponseDTO(saved);
    }
}

package com.parceldelivery.core.service;

import com.parceldelivery.core.dto.ParcelRequestDTO;
import com.parceldelivery.core.dto.ParcelResponseDTO;
import com.parceldelivery.core.entity.Parcel;
import com.parceldelivery.core.entity.User;
import com.parceldelivery.core.mapper.ParcelMapper;
import com.parceldelivery.core.repository.ParcelRepository;
import com.parceldelivery.core.security.CurrentUserProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ParcelServiceTest {

    @Mock
    private ParcelRepository parcelRepository;

    @Mock
    private CurrentUserProvider currentUserProvider;

    @Mock
    private ParcelMapper parcelMapper;

    @InjectMocks
    private ParcelService parcelService;

    @Test
    void createParcel_assignsParcelToTheAuthenticatedUser() {
        // Arrange
        User user = new User();
        user.setId(1L);

        ParcelRequestDTO dto = new ParcelRequestDTO();
        dto.setName("Guitar");
        dto.setDescription("Acoustic guitar, handle with care");

        Parcel savedParcel = new Parcel();
        savedParcel.setId(1L);
        savedParcel.setUser(user);

        ParcelResponseDTO expectedDto = new ParcelResponseDTO();
        expectedDto.setId(1L);
        expectedDto.setUserId(1L);
        expectedDto.setName("Guitar");

        when(currentUserProvider.getCurrentUser()).thenReturn(user);
        when(parcelRepository.save(any(Parcel.class))).thenReturn(savedParcel);
        when(parcelMapper.toResponseDTO(savedParcel)).thenReturn(expectedDto);

        // Act
        ParcelResponseDTO result = parcelService.createParcel(dto);

        // Assert
        assertThat(result.getUserId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Guitar");
    }
}

package com.parceldelivery.core.service;

import com.parceldelivery.core.dto.DriverRequestDTO;
import com.parceldelivery.core.dto.DriverResponseDTO;
import com.parceldelivery.core.entity.Driver;
import com.parceldelivery.core.entity.User;
import com.parceldelivery.core.exception.BadRequestException;
import com.parceldelivery.core.mapper.DriverMapper;
import com.parceldelivery.core.repository.DriverRepository;
import com.parceldelivery.core.security.CurrentUserProvider;
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
class DriverServiceTest {

    @Mock
    private DriverRepository driverRepository;

    @Mock
    private CurrentUserProvider currentUserProvider;

    @Mock
    private DriverMapper driverMapper;

    @InjectMocks
    private DriverService driverService;

    @Test
    void becomeDriver_succeeds_whenUserIsNotAlreadyADriver() {
        // Arrange
        User user = new User();
        user.setId(1L);

        DriverRequestDTO dto = new DriverRequestDTO();
        dto.setPhoneNumber("+447700900123");
        dto.setAddress("1 Baker Street");

        Driver savedDriver = new Driver();
        savedDriver.setId(1L);
        savedDriver.setUser(user);

        DriverResponseDTO expectedDto = new DriverResponseDTO();
        expectedDto.setId(1L);
        expectedDto.setUserId(1L);

        when(currentUserProvider.getCurrentUser()).thenReturn(user);
        when(driverRepository.findByUserId(1L)).thenReturn(Optional.empty());
        when(driverRepository.save(any(Driver.class))).thenReturn(savedDriver);
        when(driverMapper.toResponseDTO(savedDriver)).thenReturn(expectedDto);

        // Act
        DriverResponseDTO result = driverService.becomeDriver(dto);

        // Assert
        assertThat(result.getUserId()).isEqualTo(1L);
    }

    @Test
    void becomeDriver_throwsBadRequest_whenUserIsAlreadyADriver() {
        // Arrange
        User user = new User();
        user.setId(1L);

        when(currentUserProvider.getCurrentUser()).thenReturn(user);
        when(driverRepository.findByUserId(1L)).thenReturn(Optional.of(new Driver()));

        // Act + Assert
        assertThrows(BadRequestException.class, () -> driverService.becomeDriver(new DriverRequestDTO()));
    }
}

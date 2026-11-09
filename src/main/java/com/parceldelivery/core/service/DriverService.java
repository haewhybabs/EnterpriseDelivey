package com.parceldelivery.core.service;

import org.springframework.stereotype.Service;
import com.parceldelivery.core.exception.BadRequestException;
import com.parceldelivery.core.dto.DriverRequestDTO;
import com.parceldelivery.core.dto.DriverResponseDTO;
import com.parceldelivery.core.entity.Driver;
import com.parceldelivery.core.entity.User;
import com.parceldelivery.core.mapper.DriverMapper;
import com.parceldelivery.core.repository.DriverRepository;
import com.parceldelivery.core.security.CurrentUserProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DriverService {
    private final DriverRepository driverRepository;
    private final CurrentUserProvider currentUserProvider;
    private final DriverMapper driverMapper;

    public DriverResponseDTO becomeDriver(DriverRequestDTO dto) {
        User user = currentUserProvider.getCurrentUser();

        if (driverRepository.findByUserId(user.getId()).isPresent()) {
            throw new BadRequestException("User is already registered as a driver");
        }
        Driver driver = new Driver();
        driver.setUser(user);
        driver.setPhoneNumber(dto.getPhoneNumber());
        driver.setAddress(dto.getAddress());
        Driver saved = driverRepository.save(driver);
        return driverMapper.toResponseDTO(saved);
    }

}

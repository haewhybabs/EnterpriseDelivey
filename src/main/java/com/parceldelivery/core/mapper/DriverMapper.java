package com.parceldelivery.core.mapper;

import org.springframework.stereotype.Component;

import com.parceldelivery.core.dto.DriverResponseDTO;
import com.parceldelivery.core.entity.Driver;

@Component
public class DriverMapper {
    public DriverResponseDTO toResponseDTO(Driver driver) {
        DriverResponseDTO dto = new DriverResponseDTO();
        dto.setId(driver.getId());
        dto.setUserId(driver.getUser().getId());
        dto.setPhoneNumber(driver.getPhoneNumber());
        dto.setAddress(driver.getAddress());
        return dto;
    }

}

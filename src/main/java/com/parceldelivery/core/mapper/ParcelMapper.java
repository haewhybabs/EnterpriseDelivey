package com.parceldelivery.core.mapper;

import org.springframework.stereotype.Component;

import com.parceldelivery.core.dto.ParcelResponseDTO;
import com.parceldelivery.core.entity.Parcel;

@Component
public class ParcelMapper {
    public ParcelResponseDTO toResponseDTO(Parcel parcel) {
        ParcelResponseDTO dto = new ParcelResponseDTO();
        dto.setId(parcel.getId());
        dto.setUserId(parcel.getUser().getId());
        dto.setName(parcel.getName());
        dto.setDescription(parcel.getDescription());
        return dto;
    }
}

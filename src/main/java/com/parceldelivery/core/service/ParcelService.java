package com.parceldelivery.core.service;

import org.springframework.stereotype.Service;

import com.parceldelivery.core.dto.ParcelRequestDTO;
import com.parceldelivery.core.dto.ParcelResponseDTO;
import com.parceldelivery.core.entity.Parcel;
import com.parceldelivery.core.entity.User;
import com.parceldelivery.core.mapper.ParcelMapper;
import com.parceldelivery.core.repository.ParcelRepository;
import com.parceldelivery.core.security.CurrentUserProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ParcelService {
    private final ParcelRepository parcelRepository;
    private final CurrentUserProvider currentUserProvider;
    private final ParcelMapper parcelMapper;

    public ParcelResponseDTO createParcel(ParcelRequestDTO dto) {
        User user = currentUserProvider.getCurrentUser();

        Parcel parcel = new Parcel();
        parcel.setUser(user);
        parcel.setName(dto.getName());
        parcel.setDescription(dto.getDescription());

        Parcel saved = parcelRepository.save(parcel);
        return parcelMapper.toResponseDTO(saved);
    }
}

package com.parceldelivery.core.service;

import org.springframework.stereotype.Service;

import com.parceldelivery.core.dto.RecipientAddressRequestDTO;
import com.parceldelivery.core.dto.RecipientAddressResponseDTO;
import com.parceldelivery.core.entity.RecipientAddress;
import com.parceldelivery.core.entity.User;
import com.parceldelivery.core.exception.ResourceNotFoundException;
import com.parceldelivery.core.mapper.RecipientAddressMapper;
import com.parceldelivery.core.repository.RecipientAddressRepository;
import com.parceldelivery.core.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecipientAddressService {
    private final RecipientAddressRepository recipientAddressRepository;
    private final UserRepository userRepository;
    private final RecipientAddressMapper recipientAddressMapper;

    public RecipientAddressResponseDTO createRecipientAddress(RecipientAddressRequestDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + dto.getUserId()));

        RecipientAddress recipientAddress = new RecipientAddress();
        recipientAddress.setUser(user);
        recipientAddress.setAddress(dto.getAddress());
        recipientAddress.setRecipientEmail(dto.getRecipientEmail());
        recipientAddress.setRecipientPhoneNumber(dto.getRecipientPhoneNumber());
        RecipientAddress saved = recipientAddressRepository.save(recipientAddress);
        return recipientAddressMapper.toResponseDTO(saved);
    }
}

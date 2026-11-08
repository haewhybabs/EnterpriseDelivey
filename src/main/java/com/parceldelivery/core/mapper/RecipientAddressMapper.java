package com.parceldelivery.core.mapper;

import org.springframework.stereotype.Component;

import com.parceldelivery.core.dto.RecipientAddressResponseDTO;
import com.parceldelivery.core.entity.RecipientAddress;

@Component
public class RecipientAddressMapper {
    public RecipientAddressResponseDTO toResponseDTO(RecipientAddress recipientAddress) {
        RecipientAddressResponseDTO dto = new RecipientAddressResponseDTO();
        dto.setId(recipientAddress.getId());
        dto.setUserId(recipientAddress.getUser().getId());
        dto.setAddress(recipientAddress.getAddress());
        dto.setRecipientEmail(recipientAddress.getRecipientEmail());
        dto.setRecipientPhoneNumber(recipientAddress.getRecipientPhoneNumber());
        return dto;
    }
}

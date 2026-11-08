package com.parceldelivery.core.mapper;

import org.springframework.stereotype.Component;

import com.parceldelivery.core.dto.DeliveryRequestResponseDTO;
import com.parceldelivery.core.entity.DeliveryRequest;

@Component
public class DeliveryRequestMapper {
    public DeliveryRequestResponseDTO toResponseDTO(DeliveryRequest deliveryRequest) {
        DeliveryRequestResponseDTO dto = new DeliveryRequestResponseDTO();
        dto.setId(deliveryRequest.getId());
        dto.setDeliveryDate(deliveryRequest.getDeliveryDate());
        dto.setDeliveryStatusId(deliveryRequest.getDeliveryStatus().getId());
        dto.setDriverId(deliveryRequest.getDriver() != null ? deliveryRequest.getDriver().getId() : null);
        dto.setParcelId(deliveryRequest.getParcel().getId());
        dto.setRecipientAddressId(deliveryRequest.getRecipientAddress().getId());
        dto.setUserId(deliveryRequest.getUser().getId());
        dto.setSenderAddress(deliveryRequest.getSenderAddress());
        return dto;
    }
}

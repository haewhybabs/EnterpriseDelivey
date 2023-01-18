package com.ParcelDelivery.EnterpriseParcelDelivery.factory;

import com.ParcelDelivery.EnterpriseParcelDelivery.dto.DeliveryRequestDTO;
import com.ParcelDelivery.EnterpriseParcelDelivery.dto.DeliveryRequestResponseDTO;
import com.ParcelDelivery.EnterpriseParcelDelivery.entity.*;
import org.springframework.stereotype.Service;

@Service
public class DeliveryRequestFactory {
    public DeliveryRequest createEntity(DeliveryRequestDTO dto, Driver driver, User user, DeliveryStatus deliveryStatus, Parcel parcel){
        DeliveryRequest deliveryRequest = new DeliveryRequest();
        deliveryRequest.setDriver(driver);
        deliveryRequest.setUser(user);
        deliveryRequest.setParcel(parcel);
        deliveryRequest.setDeliveryDate(dto.getDelivery_date());
        deliveryRequest.setDeliveryStatus(deliveryStatus);
        deliveryRequest.setRecipient_address_id(dto.getRecipient_address_id());
        deliveryRequest.setSender_address(dto.getSender_address());
        return deliveryRequest;
    }

    public DeliveryRequest updateEntity(DeliveryRequest deliveryRequest,DeliveryRequestDTO deliveryRequestDTO,Driver driver, User user, Parcel parcel, DeliveryStatus deliveryStatus){
        deliveryRequest.setDriver(driver);
        deliveryRequest.setUser(user);
        deliveryRequest.setParcel(parcel);
        deliveryRequest.setDeliveryDate(deliveryRequestDTO.getDelivery_date());
        deliveryRequest.setDeliveryStatus(deliveryStatus);
        deliveryRequest.setRecipient_address_id(deliveryRequestDTO.getRecipient_address_id());
        deliveryRequest.setSender_address(deliveryRequestDTO.getSender_address());
        return deliveryRequest;

    }
    public DeliveryRequestResponseDTO createEntityResponse (DeliveryRequest deliveryRequest){
        DeliveryRequestResponseDTO dto = new DeliveryRequestResponseDTO();
        dto.setId(deliveryRequest.getId());
        dto.setDelivery_date(deliveryRequest.getDeliveryDate());
        dto.setUser_id(deliveryRequest.getUser().getId());
        dto.setDeliveryStatus(deliveryRequest.getDeliveryStatus());
        dto.setSender_address(deliveryRequest.getSender_address());
        dto.setParcel(deliveryRequest.getParcel());
        dto.setRecipient_address_id(deliveryRequest.getRecipient_address_id());
        dto.setDriver(deliveryRequest.getDriver());
        return dto;
    }
}

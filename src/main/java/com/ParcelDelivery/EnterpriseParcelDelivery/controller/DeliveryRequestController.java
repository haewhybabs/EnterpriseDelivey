package com.ParcelDelivery.EnterpriseParcelDelivery.controller;

import com.ParcelDelivery.EnterpriseParcelDelivery.dto.DeliveryRequestDTO;
import com.ParcelDelivery.EnterpriseParcelDelivery.dto.DeliveryRequestResponseDTO;
import com.ParcelDelivery.EnterpriseParcelDelivery.entity.DeliveryRequest;
import com.ParcelDelivery.EnterpriseParcelDelivery.service.DeliveryRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class DeliveryRequestController {
    @Autowired
    private DeliveryRequestService service;

    @PostMapping("/request/create")
    public DeliveryRequestResponseDTO saveDeliveryRequest(@RequestBody @Valid DeliveryRequestDTO deliveryRequestDTO){
        DeliveryRequest deliveryRequest = service.saveDeliveryRequest(deliveryRequestDTO);
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

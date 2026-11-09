package com.parceldelivery.core.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.parceldelivery.core.dto.AssignDriverRequestDTO;
import com.parceldelivery.core.dto.DeliveryRequestRequestDTO;
import com.parceldelivery.core.dto.DeliveryRequestResponseDTO;
import com.parceldelivery.core.dto.UpdateDeliveryStatusRequestDTO;
import com.parceldelivery.core.service.DeliveryRequestService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/delivery-requests")
public class DeliveryRequestController {

    private final DeliveryRequestService deliveryRequestService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public DeliveryRequestResponseDTO create(@RequestBody DeliveryRequestRequestDTO dto) {
        return deliveryRequestService.createDeliveryRequest(dto);
    }

    @PatchMapping("/{id}/assign-driver")
    public DeliveryRequestResponseDTO assignDriver(@PathVariable Long id, @RequestBody AssignDriverRequestDTO dto) {
        return deliveryRequestService.assignDriver(id, dto);
    }

    @PatchMapping("/{id}/status")
    public DeliveryRequestResponseDTO updateStatus(@PathVariable Long id, @RequestBody UpdateDeliveryStatusRequestDTO dto) {
        return deliveryRequestService.updateStatus(id, dto);
    }

}

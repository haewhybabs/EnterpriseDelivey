package com.ParcelDelivery.EnterpriseParcelDelivery.controller;

import com.ParcelDelivery.EnterpriseParcelDelivery.dto.DeliveryRequestDTO;
import com.ParcelDelivery.EnterpriseParcelDelivery.dto.DeliveryRequestResponseDTO;
import com.ParcelDelivery.EnterpriseParcelDelivery.entity.DeliveryRequest;
import com.ParcelDelivery.EnterpriseParcelDelivery.service.DeliveryRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class DeliveryRequestController {
    @Autowired
    private DeliveryRequestService service;

    @PostMapping("/request/create")
    public DeliveryRequestResponseDTO saveDeliveryRequest(@RequestBody @Valid DeliveryRequestDTO deliveryRequestDTO){
        return service.saveDeliveryRequest(deliveryRequestDTO);

    }
    @PutMapping("/request/update")
    public DeliveryRequestResponseDTO updateDeliveryRequest(@RequestBody @Valid DeliveryRequestDTO deliveryRequestDTO){
        return service.updateRequest(deliveryRequestDTO);

    }
    @PutMapping("/request/assign-driver")
    public DeliveryRequestResponseDTO assignRequestDriver(@RequestBody DeliveryRequestDTO deliveryRequestDTO){
        return service.assignDriver(deliveryRequestDTO);
    }
    @PutMapping("/request/update-status")
    public DeliveryRequestResponseDTO updateRequestStatus (@RequestBody DeliveryRequestDTO deliveryRequestDTO){
        return service.updateRequestStatus(deliveryRequestDTO);
    }
    @GetMapping("/requests")
    public List<DeliveryRequestResponseDTO> findDeliveryRequests(@RequestParam(value="driver_id",required = false) Integer driver_id){
        return service.getDeliveryRequests(driver_id);
    }
    @GetMapping("/request/filter")
    public List<DeliveryRequestResponseDTO> findDeliveryByFilter(@RequestParam(value="user_id",required = true) Integer user_id,@RequestParam(value="delivery_status_id",required = false) Integer delivery_status_id){
        return service.getDeliveryRequestsByUserIdAndDeliveryStatusId(user_id,delivery_status_id);
    }
}

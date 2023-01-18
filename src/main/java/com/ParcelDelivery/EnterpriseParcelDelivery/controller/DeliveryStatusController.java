package com.ParcelDelivery.EnterpriseParcelDelivery.controller;

import com.ParcelDelivery.EnterpriseParcelDelivery.entity.DeliveryStatus;
import com.ParcelDelivery.EnterpriseParcelDelivery.service.DeliveryStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class DeliveryStatusController {
    @Autowired
    private DeliveryStatusService service;

    @PostMapping("/status/add")
    public ResponseEntity<DeliveryStatus> saveStatus(@RequestBody @Valid DeliveryStatus deliveryStatus){
        return new ResponseEntity<>(service.saveStatus(deliveryStatus), HttpStatus.CREATED);
    }
    @GetMapping("/statuses")
    public ResponseEntity<List> findAllStatus(){
        return ResponseEntity.ok(service.getStatuses());
    }
    @GetMapping("/status/{id}")
    public DeliveryStatus findStatusById(@PathVariable int id){
        return service.getStatusById(id);
    }
    @PutMapping("/status/update")
    public DeliveryStatus updateStatus(@RequestBody DeliveryStatus deliveryStatus){
        return service.updateStatus(deliveryStatus);
    }
    @DeleteMapping("/status/delete/{id}")
    public String deleteStatus(@PathVariable int id){
        return service.deleteStatus(id);
    }
}

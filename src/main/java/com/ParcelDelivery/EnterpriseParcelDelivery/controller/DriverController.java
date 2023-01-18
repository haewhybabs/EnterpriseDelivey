package com.ParcelDelivery.EnterpriseParcelDelivery.controller;

import com.ParcelDelivery.EnterpriseParcelDelivery.dto.DriverDTO;
import com.ParcelDelivery.EnterpriseParcelDelivery.entity.Driver;
import com.ParcelDelivery.EnterpriseParcelDelivery.service.DriverService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class DriverController {
    @Autowired
    private DriverService service;

    @PostMapping("/driver/add")
    public ResponseEntity<DriverDTO> saveDriver(@RequestBody @Valid DriverDTO driverDTO){
        return new ResponseEntity<>(service.saveDriver(driverDTO), HttpStatus.CREATED);
    }
    @GetMapping("/drivers")
    public ResponseEntity<List> findAllDrivers(){
        return ResponseEntity.ok(service.getDrivers());
    }
    @GetMapping("/driver/{id}")
    public DriverDTO getDriverById(@PathVariable int id){
        return service.findByDriverId(id);
    }
    @PutMapping("/driver/update")
    public Driver updateDriver(@RequestBody Driver driver){
        return service.updateDriver(driver);
    }
    @DeleteMapping("/driver/delete")
    public String deleteDriver(@PathVariable int id){
        return service.deleteDriver(id);
    }

}

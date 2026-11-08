package com.parceldelivery.core.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.parceldelivery.core.dto.DriverRequestDTO;
import com.parceldelivery.core.dto.DriverResponseDTO;
import com.parceldelivery.core.service.DriverService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/drivers")
@RequiredArgsConstructor
public class DriverController {

    private final DriverService driverService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public DriverResponseDTO create(@RequestBody DriverRequestDTO dto) {
        return driverService.becomeDriver(dto);
    }

}

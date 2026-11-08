package com.parceldelivery.core.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.parceldelivery.core.dto.ParcelRequestDTO;
import com.parceldelivery.core.dto.ParcelResponseDTO;
import com.parceldelivery.core.service.ParcelService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/parcels")
@RequiredArgsConstructor
public class ParcelController {
    private final ParcelService parcelService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParcelResponseDTO createParcel(@RequestBody ParcelRequestDTO dto) {
        return parcelService.createParcel(dto);
    }
}

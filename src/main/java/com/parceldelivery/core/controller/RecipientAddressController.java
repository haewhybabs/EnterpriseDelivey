package com.parceldelivery.core.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.parceldelivery.core.dto.RecipientAddressRequestDTO;
import com.parceldelivery.core.dto.RecipientAddressResponseDTO;
import com.parceldelivery.core.service.RecipientAddressService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/recipient-addresses")
@RequiredArgsConstructor
public class RecipientAddressController {

    private final RecipientAddressService recipientAddressService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RecipientAddressResponseDTO createRecipientAddress(@RequestBody RecipientAddressRequestDTO dto) {
        return recipientAddressService.createRecipientAddress(dto);
    }

}

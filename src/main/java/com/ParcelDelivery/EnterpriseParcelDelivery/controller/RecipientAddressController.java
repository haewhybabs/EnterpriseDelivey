package com.ParcelDelivery.EnterpriseParcelDelivery.controller;

import com.ParcelDelivery.EnterpriseParcelDelivery.dto.RecipientAddressDTO;
import com.ParcelDelivery.EnterpriseParcelDelivery.entity.RecipientAddress;
import com.ParcelDelivery.EnterpriseParcelDelivery.entity.User;
import com.ParcelDelivery.EnterpriseParcelDelivery.service.RecipientAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RecipientAddressController {
    @Autowired
    private RecipientAddressService service;

    @PostMapping("/recipient-address/save")
    public RecipientAddressDTO saveRecipientAddress(@RequestBody @Valid RecipientAddressDTO recipientAddressDTO){
        RecipientAddress recipientAddress = service.saveRecipientAddress(recipientAddressDTO);
        recipientAddressDTO.setId(recipientAddress.getId());
        return recipientAddressDTO;
    }

    @PutMapping("/recipient-address/update")
    public RecipientAddressDTO updateRecipientAddress(@RequestBody @Valid RecipientAddressDTO recipientAddressDTO){
        service.updateRecipientAddress(recipientAddressDTO);
        return recipientAddressDTO;

    }
    @GetMapping("/recipient-address")
    public ResponseEntity<List<RecipientAddressDTO>> findAllAddress(@RequestParam(value="user_id",required = false) Integer user_id){
        List<RecipientAddress> recipientAddresses = service.getRecipientAddresses();
        if(user_id !=null){
          recipientAddresses = service.findRecipientAddressByUserId(user_id);
        }
        List<RecipientAddressDTO> dtos = new ArrayList<>();
        for (RecipientAddress recipientAddress: recipientAddresses){
            RecipientAddressDTO dto = new RecipientAddressDTO();
            dto.setId(recipientAddress.getId());
            dto.setAddress(recipientAddress.getAddress());
            dto.setRecipient_phone_number(recipientAddress.getRecipient_phone_number());
            dto.setRecipient_email(recipientAddress.getRecipient_email());
            dto.setUser_id(recipientAddress.getUser().getId());
            dtos.add(dto);
        }
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/recipient-address/{id}")
    public RecipientAddressDTO findRecipientAddressById(@PathVariable int id){
        RecipientAddress recipientAddress = service.getRecipientAddressById(id);
        RecipientAddressDTO dto = new RecipientAddressDTO();
        dto.setAddress(recipientAddress.getAddress());
        dto.setId(recipientAddress.getId());
        dto.setUser_id(recipientAddress.getUser().getId());
        dto.setRecipient_phone_number(recipientAddress.getRecipient_phone_number());
        dto.setRecipient_email(recipientAddress.getRecipient_email());

        return dto;
    }
    @DeleteMapping("/recipient-address/delete/{id}")
    public String deleteRecipientAddress(@PathVariable int id){
        return service.deleteRecipientAddress(id);
    }
}

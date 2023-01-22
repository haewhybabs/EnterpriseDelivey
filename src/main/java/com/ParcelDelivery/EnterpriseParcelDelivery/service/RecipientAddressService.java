package com.ParcelDelivery.EnterpriseParcelDelivery.service;

import com.ParcelDelivery.EnterpriseParcelDelivery.dto.RecipientAddressDTO;
import com.ParcelDelivery.EnterpriseParcelDelivery.entity.RecipientAddress;
import com.ParcelDelivery.EnterpriseParcelDelivery.entity.User;
import com.ParcelDelivery.EnterpriseParcelDelivery.factory.RecipientAddressFactory;
import com.ParcelDelivery.EnterpriseParcelDelivery.repository.RecipientAddressRepository;
import com.ParcelDelivery.EnterpriseParcelDelivery.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Slf4j
@Service
public class RecipientAddressService {
    @Autowired
    private RecipientAddressRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RecipientAddressFactory recipientAddressFactory;



    public RecipientAddressDTO saveRecipientAddress(RecipientAddressDTO recipientAddressDTO){
//        recipientAddressDTO.setUser_id();
        RecipientAddress recipientAddress = recipientAddressFactory.createEntity(recipientAddressDTO);
        repository.save(recipientAddress);
        RecipientAddressDTO dto = recipientAddressFactory.responseEntity(recipientAddress);
        return dto;
    }

    public List<RecipientAddressDTO> getRecipientAddresses(){
        List<RecipientAddress> recipientAddresses = repository.findAll();
        List<RecipientAddressDTO> dtos = new ArrayList<>();
        for (RecipientAddress recipientAddress: recipientAddresses){
            RecipientAddressDTO dto = recipientAddressFactory.responseEntity(recipientAddress);
            dtos.add(dto);
        }
        return dtos;

    }
    public RecipientAddressDTO getRecipientAddressById(int id){
        RecipientAddress recipientAddress = repository.findById(id).orElse(null);
        RecipientAddressDTO dto = recipientAddressFactory.responseEntity(recipientAddress);
        return dto;
    }
    public List<RecipientAddressDTO> findRecipientAddressByUserId(int user_id){
         List<RecipientAddress> recipientAddresses = repository.findRecipientAddressByUserId(user_id);
        List<RecipientAddressDTO> dtos = new ArrayList<>();
        for (RecipientAddress recipientAddress: recipientAddresses){
            RecipientAddressDTO dto = recipientAddressFactory.responseEntity(recipientAddress);
            dtos.add(dto);
        }
        return dtos;
    }
    public RecipientAddressDTO updateRecipientAddress(RecipientAddressDTO recipientAddressDTO){
        RecipientAddress existingAddress = repository.findById(recipientAddressDTO.getId()).orElse(null);
        RecipientAddress recipientAddress = recipientAddressFactory.updateEntity(existingAddress,recipientAddressDTO);
        RecipientAddress updatedAddress = repository.save(recipientAddress);
        RecipientAddressDTO dto = recipientAddressFactory.responseEntity(updatedAddress);
        return dto;
    }
    public String deleteRecipientAddress(int id){
        repository.deleteById(id);
       String message = "Address deleted successfully";
        return message;
    }


}

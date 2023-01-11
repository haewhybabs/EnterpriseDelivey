package com.ParcelDelivery.EnterpriseParcelDelivery.service;

import com.ParcelDelivery.EnterpriseParcelDelivery.dto.RecipientAddressDTO;
import com.ParcelDelivery.EnterpriseParcelDelivery.entity.RecipientAddress;
import com.ParcelDelivery.EnterpriseParcelDelivery.entity.User;
import com.ParcelDelivery.EnterpriseParcelDelivery.repository.RecipientAddressRepository;
import com.ParcelDelivery.EnterpriseParcelDelivery.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
public class RecipientAddressService {
    @Autowired
    private RecipientAddressRepository repository;

    @Autowired
    private UserRepository userRepository;


    public RecipientAddress saveRecipientAddress(RecipientAddressDTO recipientAddressDTO){
        User user = userRepository.findById(recipientAddressDTO.getUser_id()).orElse(null);

        RecipientAddress recipientAddress = new RecipientAddress();
        recipientAddress.setAddress(recipientAddressDTO.getAddress());
        recipientAddress.setUser(user);
        recipientAddress.setRecipient_phone_number(recipientAddressDTO.getRecipient_phone_number());
        recipientAddress.setRecipient_email(recipientAddressDTO.getRecipient_email());
        return repository.save(recipientAddress);
    }

    public List<RecipientAddress> getRecipientAddresses(){
        return repository.findAll();
    }
    public RecipientAddress getRecipientAddressById(int id){
        return repository.findById(id).orElse(null);
    }
    public List<RecipientAddress> findRecipientAddressByUserId(int user_id){
        return repository.findRecipientAddressByUserId(user_id);
    }
    public RecipientAddress updateRecipientAddress(RecipientAddressDTO recipientAddressDTO){
        RecipientAddress existingAddress = repository.findById(recipientAddressDTO.getId()).orElse(null);
        existingAddress.setAddress(recipientAddressDTO.getAddress());
        existingAddress.setRecipient_email(recipientAddressDTO.getRecipient_email());
        existingAddress.setRecipient_phone_number(recipientAddressDTO.getRecipient_phone_number());
        return repository.save(existingAddress);
    }
    public String deleteRecipientAddress(int id){
        repository.deleteById(id);
       String message = "Address deleted successfully";
        return message;
    }


}

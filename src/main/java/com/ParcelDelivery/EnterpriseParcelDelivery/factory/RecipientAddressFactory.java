package com.ParcelDelivery.EnterpriseParcelDelivery.factory;

import com.ParcelDelivery.EnterpriseParcelDelivery.dto.RecipientAddressDTO;
import com.ParcelDelivery.EnterpriseParcelDelivery.entity.RecipientAddress;
import com.ParcelDelivery.EnterpriseParcelDelivery.entity.User;
import com.ParcelDelivery.EnterpriseParcelDelivery.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecipientAddressFactory {

    @Autowired
    private UserRepository userRepository;
    public RecipientAddress createEntity(RecipientAddressDTO dto){
        User user = userRepository.findById(dto.getUser_id()).orElse(null);
        RecipientAddress recipientAddress = new RecipientAddress();
        recipientAddress.setAddress(dto.getAddress());
        recipientAddress.setUser(user);
        recipientAddress.setRecipient_phone_number(dto.getRecipient_phone_number());
        recipientAddress.setRecipient_email(dto.getRecipient_email());
        return recipientAddress;
    }

    public RecipientAddress updateEntity(RecipientAddress recipientAddress, RecipientAddressDTO dto){
        recipientAddress.setAddress(dto.getAddress());
        recipientAddress.setRecipient_email(dto.getRecipient_email());
        recipientAddress.setRecipient_phone_number(dto.getRecipient_phone_number());
        return recipientAddress;
    }
}

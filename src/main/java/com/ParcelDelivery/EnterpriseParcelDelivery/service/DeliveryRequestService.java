package com.ParcelDelivery.EnterpriseParcelDelivery.service;

import com.ParcelDelivery.EnterpriseParcelDelivery.dto.DeliveryRequestDTO;
import com.ParcelDelivery.EnterpriseParcelDelivery.entity.*;
import com.ParcelDelivery.EnterpriseParcelDelivery.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class DeliveryRequestService {
    @Autowired
    private DeliveryRequestRepository deliveryRequestRepository;
    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DeliveryStatusRepository deliveryStatusRepository;
    @Autowired
    private ParcelRepository parcelRepository;


    public DeliveryRequest saveDeliveryRequest(DeliveryRequestDTO deliveryRequestDTO){
        Driver driver = driverRepository.findById(deliveryRequestDTO.getDriver_id()).orElse(null);
        User user  = userRepository.findById(deliveryRequestDTO.getUser_id()).orElse(null);
        Parcel parcel = parcelRepository.findById(deliveryRequestDTO.getParcel_id()).orElse(null);
        DeliveryStatus deliveryStatus = deliveryStatusRepository.findById(1).orElse(null);

        DeliveryRequest deliveryRequest = new DeliveryRequest();
        deliveryRequest.setDriver(driver);
        deliveryRequest.setUser(user);
        deliveryRequest.setParcel(parcel);
        deliveryRequest.setDeliveryDate(deliveryRequestDTO.getDelivery_date());
        deliveryRequest.setDeliveryStatus(deliveryStatus);
        deliveryRequest.setRecipient_address_id(deliveryRequestDTO.getRecipient_address_id());
        deliveryRequest.setSender_address(deliveryRequestDTO.getSender_address());

        return deliveryRequestRepository.save(deliveryRequest);

    }
    public List<DeliveryRequest> getDeliveryRequests(){
        return deliveryRequestRepository.findAll();
    }
    public DeliveryRequest findDeliveryRequestById(int id){
        return deliveryRequestRepository.findById(id).orElse(null);
    }

}

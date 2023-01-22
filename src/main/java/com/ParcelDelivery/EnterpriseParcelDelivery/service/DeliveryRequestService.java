package com.ParcelDelivery.EnterpriseParcelDelivery.service;

import com.ParcelDelivery.EnterpriseParcelDelivery.advice.BadRequestException;
import com.ParcelDelivery.EnterpriseParcelDelivery.dto.DeliveryRequestDTO;
import com.ParcelDelivery.EnterpriseParcelDelivery.dto.DeliveryRequestResponseDTO;
import com.ParcelDelivery.EnterpriseParcelDelivery.entity.*;
import com.ParcelDelivery.EnterpriseParcelDelivery.factory.DeliveryRequestFactory;
import com.ParcelDelivery.EnterpriseParcelDelivery.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Autowired
    DeliveryRequestFactory deliveryRequestFactory;


    public DeliveryRequestResponseDTO saveDeliveryRequest(DeliveryRequestDTO deliveryRequestDTO){
        Driver driver = driverRepository.findById(deliveryRequestDTO.getDriver_id()).orElse(null);
        User user  = userRepository.findById(deliveryRequestDTO.getUser_id()).orElse(null);
        Parcel parcel = parcelRepository.findById(deliveryRequestDTO.getParcel_id()).orElse(null);
        DeliveryStatus deliveryStatus = deliveryStatusRepository.findById(1).orElse(null);

        DeliveryRequest newDeliveryRequest = deliveryRequestFactory.createEntity(deliveryRequestDTO,driver,user,deliveryStatus,parcel);
        DeliveryRequest deliveryRequest = deliveryRequestRepository.save(newDeliveryRequest);
        return deliveryRequestFactory.createEntityResponse(deliveryRequest);
    }
    public DeliveryRequestResponseDTO updateRequestStatus(DeliveryRequestDTO deliveryRequestDTO){
        DeliveryRequest existingRequest  = deliveryRequestRepository.findById(deliveryRequestDTO.getId()).orElse(null);
        DeliveryStatus deliveryStatus = deliveryStatusRepository.findById(deliveryRequestDTO.getDelivery_status_id()).orElse(null);
        if(existingRequest==null){
            throw new BadRequestException("Request not found");
        }
        existingRequest.setDeliveryStatus(deliveryStatus);
        deliveryRequestRepository.save(existingRequest);
        return deliveryRequestFactory.createEntityResponse(existingRequest);

    }
    public DeliveryRequestResponseDTO assignDriver(DeliveryRequestDTO deliveryRequestDTO){
        DeliveryRequest existingRequest  = deliveryRequestRepository.findById(deliveryRequestDTO.getId()).orElse(null);
        Driver driver = driverRepository.findById(deliveryRequestDTO.getDriver_id()).orElse(null);
        if(driver==null){
            throw new BadRequestException("Driver not found");
        }
        if(existingRequest==null){
            throw new BadRequestException("Request not found");
        }
        existingRequest.setDriver(driver);
        deliveryRequestRepository.save(existingRequest);
        return deliveryRequestFactory.createEntityResponse(existingRequest);
    }
    public DeliveryRequestResponseDTO updateRequest(DeliveryRequestDTO deliveryRequestDTO){
        Driver driver = driverRepository.findById(deliveryRequestDTO.getDriver_id()).orElse(null);
        User user  = userRepository.findById(deliveryRequestDTO.getUser_id()).orElse(null);
        Parcel parcel = parcelRepository.findById(deliveryRequestDTO.getParcel_id()).orElse(null);
        DeliveryStatus deliveryStatus = deliveryStatusRepository.findById(1).orElse(null);
        DeliveryRequest deliveryRequest = deliveryRequestRepository.findById(deliveryRequestDTO.getId()).orElse(null);
        if(deliveryRequest==null){
            throw new BadRequestException("Request not found");
        }
        DeliveryRequest updateRequest = deliveryRequestFactory.updateEntity(deliveryRequest,deliveryRequestDTO,driver,user,parcel,deliveryStatus);
        deliveryRequestRepository.save(updateRequest);
        return deliveryRequestFactory.createEntityResponse(deliveryRequest);

    }

    public List<DeliveryRequestResponseDTO> getDeliveryRequests(Integer driver_id){
        List<DeliveryRequest> deliveryRequests = deliveryRequestRepository.findAll();
        if(driver_id != null){
            deliveryRequests = deliveryRequestRepository.findByDriverId(driver_id);
        }
        List<DeliveryRequestResponseDTO> dtos = new ArrayList<>();
        for (DeliveryRequest deliveryRequest: deliveryRequests){
            DeliveryRequestResponseDTO dto = deliveryRequestFactory.createEntityResponse(deliveryRequest);
            dtos.add(dto);
        }
        return dtos;


    }
    public DeliveryRequestResponseDTO findDeliveryRequestById(int id){
        DeliveryRequest deliveryRequest = deliveryRequestRepository.findById(id).orElse(null);
        if(deliveryRequest==null){
            throw new BadRequestException("Request not found");
        }
        return deliveryRequestFactory.createEntityResponse(deliveryRequest);
    }
    public List<DeliveryRequestResponseDTO> getDeliveryRequestsByDriverId(int driver_id){
        List<DeliveryRequest> deliveryRequests = deliveryRequestRepository.findByDriverId(driver_id);
        List<DeliveryRequestResponseDTO> dtos = new ArrayList<>();
        for (DeliveryRequest deliveryRequest: deliveryRequests){
            DeliveryRequestResponseDTO dto = deliveryRequestFactory.createEntityResponse(deliveryRequest);
            dtos.add(dto);
        }
        return dtos;
    }
    public List<DeliveryRequestResponseDTO> getDeliveryRequestsByUserIdAndDeliveryStatusId(Integer user_id, Integer delivery_status_id){
        List<DeliveryRequest> deliveryRequests = deliveryRequestRepository.findByUserId(user_id);
        if(delivery_status_id !=null){
            deliveryRequests = deliveryRequestRepository.findByUserIdAndDeliveryStatusId(user_id,delivery_status_id);
        }
        List<DeliveryRequestResponseDTO> dtos = new ArrayList<>();
        for (DeliveryRequest deliveryRequest: deliveryRequests){
            DeliveryRequestResponseDTO dto = deliveryRequestFactory.createEntityResponse(deliveryRequest);
            dtos.add(dto);
        }
        return dtos;
    }

}

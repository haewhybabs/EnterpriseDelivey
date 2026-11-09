package com.parceldelivery.core.service;

import org.springframework.stereotype.Service;

import com.parceldelivery.core.dto.AssignDriverRequestDTO;
import com.parceldelivery.core.dto.DeliveryRequestRequestDTO;
import com.parceldelivery.core.dto.DeliveryRequestResponseDTO;
import com.parceldelivery.core.dto.UpdateDeliveryStatusRequestDTO;
import com.parceldelivery.core.entity.DeliveryRequest;
import com.parceldelivery.core.entity.DeliveryStatus;
import com.parceldelivery.core.entity.Driver;
import com.parceldelivery.core.entity.Parcel;
import com.parceldelivery.core.entity.RecipientAddress;
import com.parceldelivery.core.entity.User;
import com.parceldelivery.core.exception.BadRequestException;
import com.parceldelivery.core.exception.ResourceNotFoundException;
import com.parceldelivery.core.mapper.DeliveryRequestMapper;
import com.parceldelivery.core.repository.DeliveryRequestRepository;
import com.parceldelivery.core.repository.DeliveryStatusRepository;
import com.parceldelivery.core.repository.DriverRepository;
import com.parceldelivery.core.repository.ParcelRepository;
import com.parceldelivery.core.repository.RecipientAddressRepository;
import com.parceldelivery.core.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeliveryRequestService {
    private static final String DEFAULT_STATUS = "Awaiting delivery";
    private final DeliveryRequestRepository deliveryRequestRepository;
    private final UserRepository userRepository;
    private final DeliveryStatusRepository deliveryStatusRepository;
    private final ParcelRepository parcelRepository;
    private final RecipientAddressRepository recipientAddressRepository;
    private final DriverRepository driverRepository;
    private final DeliveryRequestMapper deliveryRequestMapper;

    public DeliveryRequestResponseDTO createDeliveryRequest(DeliveryRequestRequestDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + dto.getUserId()));

        DeliveryStatus deliveryStatus = deliveryStatusRepository.findByName(DEFAULT_STATUS)
                .orElseThrow(() -> new IllegalStateException(
                        "Default delivery status '" + DEFAULT_STATUS + "' is not seeded in the database"));

        Parcel parcel = parcelRepository.findById(dto.getParcelId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Parcel not found with id " + dto.getParcelId()));

        RecipientAddress recipientAddress = recipientAddressRepository.findById(dto.getRecipientAddressId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Recipient address not found with id " + dto.getRecipientAddressId()));

        DeliveryRequest deliveryRequest = new DeliveryRequest();
        deliveryRequest.setUser(user);
        deliveryRequest.setDeliveryStatus(deliveryStatus);
        deliveryRequest.setParcel(parcel);
        deliveryRequest.setDeliveryDate(dto.getDeliveryDate());
        deliveryRequest.setRecipientAddress(recipientAddress);
        deliveryRequest.setSenderAddress(dto.getSenderAddress());

        DeliveryRequest saved = deliveryRequestRepository.save(deliveryRequest);
        return deliveryRequestMapper.toResponseDTO(saved);
    }

    public DeliveryRequestResponseDTO assignDriver(Long deliveryRequestId, AssignDriverRequestDTO dto) {
        DeliveryRequest deliveryRequest = deliveryRequestRepository.findById(deliveryRequestId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Delivery request not found with id: " + deliveryRequestId));

        Driver driver = driverRepository.findById(dto.getDriverId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Driver not found with id: " + dto.getDriverId()));

        deliveryRequest.setDriver(driver);

        DeliveryRequest saved = deliveryRequestRepository.save(deliveryRequest);
        return deliveryRequestMapper.toResponseDTO(saved);
    }

    public DeliveryRequestResponseDTO updateStatus(Long deliveryRequestId, UpdateDeliveryStatusRequestDTO dto) {
        DeliveryRequest deliveryRequest = deliveryRequestRepository.findById(deliveryRequestId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Delivery request not found with id: " + deliveryRequestId));

        DeliveryStatus status = deliveryStatusRepository.findByName(dto.getStatusName())
                .orElseThrow(() -> new BadRequestException("Invalid delivery status: " + dto.getStatusName()));

        deliveryRequest.setDeliveryStatus(status);

        DeliveryRequest saved = deliveryRequestRepository.save(deliveryRequest);
        return deliveryRequestMapper.toResponseDTO(saved);
    }

}

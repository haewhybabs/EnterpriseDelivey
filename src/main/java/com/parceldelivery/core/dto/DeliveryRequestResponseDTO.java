package com.parceldelivery.core.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryRequestResponseDTO {
    private Long id;
    private Long userId;
    private Long recipientAddressId;
    private Long parcelId;
    private Long deliveryStatusId;
    private Long driverId;
    private String senderAddress;
    private LocalDateTime deliveryDate;

}

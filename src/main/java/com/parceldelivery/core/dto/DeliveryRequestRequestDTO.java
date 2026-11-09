package com.parceldelivery.core.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryRequestRequestDTO {
    private Long recipientAddressId;
    private Long parcelId;
    private String senderAddress;
    private LocalDateTime deliveryDate;

}

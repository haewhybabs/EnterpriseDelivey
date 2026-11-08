package com.parceldelivery.core.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipientAddressResponseDTO {
    private Long id;
    private Long userId;
    private String address;
    private String recipientPhoneNumber;
    private String recipientEmail;

}

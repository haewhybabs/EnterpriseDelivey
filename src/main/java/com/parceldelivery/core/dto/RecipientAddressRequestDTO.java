package com.parceldelivery.core.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipientAddressRequestDTO {
    private String address;
    private String recipientPhoneNumber;
    private String recipientEmail;

}

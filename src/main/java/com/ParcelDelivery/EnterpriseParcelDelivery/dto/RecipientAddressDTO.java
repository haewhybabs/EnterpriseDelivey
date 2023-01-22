package com.ParcelDelivery.EnterpriseParcelDelivery.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RecipientAddressDTO {
    private int id;

    private int user_id;
    @NotNull
    private String address;
    @NotNull
    private String recipient_phone_number;
    @NotNull
    private String recipient_email;

}

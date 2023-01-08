package com.ParcelDelivery.EnterpriseParcelDelivery.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DriverDTO {
    @NotNull
    private int user_id;
    @NotNull(message="Phone number is required")
    private String phone_number;
    @NotNull(message="Address is required")
    private String address;
    @NotNull
    private int role_id;
}

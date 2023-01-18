package com.ParcelDelivery.EnterpriseParcelDelivery.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DriverDTO {
    private int id;
    @NotNull
    private int user_id;
    @NotNull(message="Phone number is required")
    private String phone_number;
    @NotNull(message="Address is required")
    private String address;
    @NotNull
    private String email;
    @NotNull
    private String name;
    @NotNull
    private String password;


}

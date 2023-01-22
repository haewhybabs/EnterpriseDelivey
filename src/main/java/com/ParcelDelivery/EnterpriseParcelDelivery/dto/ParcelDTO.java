package com.ParcelDelivery.EnterpriseParcelDelivery.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
@Data
public class ParcelDTO {
    private int id;
    @NotNull
    private String name;
    @NotNull
    private  String  description;

    private int user_id;
}

package com.ParcelDelivery.EnterpriseParcelDelivery.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DeliveryStatusDTO {
    private int id;
    @NotNull
    private String status;
}

package com.ParcelDelivery.EnterpriseParcelDelivery.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RatingDTO {
    private int id;
    @NotNull
    private int delivery_request_id;
    @NotNull
    private int sender_rating;
}

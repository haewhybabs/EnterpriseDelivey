package com.ParcelDelivery.EnterpriseParcelDelivery.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class DeliveryRequestDTO {
    private int id;
    @NotNull
    private int user_id;
    @NotNull
    private String sender_address;
    @NotNull
    private int parcel_id;
    private LocalDateTime delivery_date;
    private int driver_id;

    @NotNull
    private int recipient_address_id;
    private int delivery_status_id;




}

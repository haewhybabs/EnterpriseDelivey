package com.ParcelDelivery.EnterpriseParcelDelivery.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class DeliveryRequestDTO {
    @NotNull
    private int user_id;
    @NotNull
    private String recipient_email;
    @NotNull
    private String recipient_phone_number;
    @NotNull
    private String sender_address;
    @NotNull
    private int parcel_id;
    private LocalDate delivery_date;
    private int driver_id;
    @NotNull
    private String delivery_address;




}

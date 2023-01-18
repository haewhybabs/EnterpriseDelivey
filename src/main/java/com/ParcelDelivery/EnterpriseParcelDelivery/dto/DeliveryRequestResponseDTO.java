package com.ParcelDelivery.EnterpriseParcelDelivery.dto;

import com.ParcelDelivery.EnterpriseParcelDelivery.entity.DeliveryStatus;
import com.ParcelDelivery.EnterpriseParcelDelivery.entity.Driver;
import com.ParcelDelivery.EnterpriseParcelDelivery.entity.Parcel;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class DeliveryRequestResponseDTO {
    private int id;
    private DeliveryStatus deliveryStatus;
    private String sender_address;
    private Parcel parcel;
    private int user_id;
    private Driver driver;
    private int recipient_address_id;
    private LocalDateTime delivery_date;

}

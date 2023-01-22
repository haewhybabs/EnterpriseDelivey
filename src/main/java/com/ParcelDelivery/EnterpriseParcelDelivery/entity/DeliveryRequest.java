package com.ParcelDelivery.EnterpriseParcelDelivery.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="delivery_request")
public class DeliveryRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name="parcel_id")
    private Parcel parcel;

    @Column(name = "delivery_date")
    private LocalDateTime deliveryDate;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "delivery_status_id")
    private DeliveryStatus deliveryStatus;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;

    private int recipient_address_id;
    private String sender_address;


}

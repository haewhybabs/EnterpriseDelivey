package com.ParcelDelivery.EnterpriseParcelDelivery.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

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

    private String recipient_email;
    private String recipient_phone_number;

    @OneToOne
    @JoinColumn(name="parcel_id")
    private Parcel parcel;

    @Column(name = "delivery_date")
    private LocalDate deliveryDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_status_id")
    private DeliveryStatus deliveryStatus;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;

    private String delivery_address;

}

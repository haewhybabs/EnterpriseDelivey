package com.ParcelDelivery.EnterpriseParcelDelivery.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="parcel")
public class Parcel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    private int user_id;

//    @OneToOne(mappedBy = "deliveryRequest")
//    private DeliveryRequest deliveryRequest;
}

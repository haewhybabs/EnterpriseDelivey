package com.ParcelDelivery.EnterpriseParcelDelivery.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="delivery_status")
public class DeliveryStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String status;
//    @OneToOne(mappedBy = "deliveryStatus")
//    private DeliveryRequest deliveryRequest;
}

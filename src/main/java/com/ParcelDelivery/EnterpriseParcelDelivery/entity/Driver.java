package com.ParcelDelivery.EnterpriseParcelDelivery.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="driver")
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    private String address;
    private String phone_number;

//    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<DeliveryRequest> deliveryRequests;

}

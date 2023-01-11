package com.ParcelDelivery.EnterpriseParcelDelivery.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="recipient_address")
public class RecipientAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
//    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

    private String address;
    private String recipient_phone_number;
    private String recipient_email;


}

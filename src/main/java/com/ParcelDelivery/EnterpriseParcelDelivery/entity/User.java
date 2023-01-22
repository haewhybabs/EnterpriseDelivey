package com.ParcelDelivery.EnterpriseParcelDelivery.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @Column(name="email", nullable = false, unique = true)
    private String email;

    private String password;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "role_id")
//    @JsonBackReference
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<DeliveryRequest> deliveryRequests;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<RecipientAddress> recipientAddresses;






}

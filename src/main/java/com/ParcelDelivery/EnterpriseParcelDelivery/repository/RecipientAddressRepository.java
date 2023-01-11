package com.ParcelDelivery.EnterpriseParcelDelivery.repository;

import com.ParcelDelivery.EnterpriseParcelDelivery.entity.RecipientAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipientAddressRepository extends JpaRepository<RecipientAddress,Integer> {

    List<RecipientAddress> findRecipientAddressByUserId(int user_id);
}

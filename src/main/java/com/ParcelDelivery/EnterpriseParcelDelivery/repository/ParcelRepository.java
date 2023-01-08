package com.ParcelDelivery.EnterpriseParcelDelivery.repository;

import com.ParcelDelivery.EnterpriseParcelDelivery.entity.Parcel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParcelRepository extends JpaRepository<Parcel,Integer> {
    Parcel findByName(String name);
}

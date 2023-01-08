package com.ParcelDelivery.EnterpriseParcelDelivery.repository;

import com.ParcelDelivery.EnterpriseParcelDelivery.entity.DeliveryStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryStatusRepository extends JpaRepository<DeliveryStatus,Integer> {
}

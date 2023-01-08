package com.ParcelDelivery.EnterpriseParcelDelivery.repository;

import com.ParcelDelivery.EnterpriseParcelDelivery.entity.DeliveryRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRequestRepository extends JpaRepository<DeliveryRequest, Integer> {

}

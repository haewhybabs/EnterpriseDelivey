package com.parceldelivery.core.repository;

import com.parceldelivery.core.entity.DeliveryRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeliveryRequestRepository extends JpaRepository<DeliveryRequest, Long> {

    List<DeliveryRequest> findByDriverId(Long driverId);

    List<DeliveryRequest> findByUserId(Long userId);

    List<DeliveryRequest> findByUserIdAndDeliveryStatusId(Long userId, Long deliveryStatusId);
}

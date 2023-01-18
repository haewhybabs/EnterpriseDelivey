package com.ParcelDelivery.EnterpriseParcelDelivery.repository;

import com.ParcelDelivery.EnterpriseParcelDelivery.entity.DeliveryRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DeliveryRequestRepository extends JpaRepository<DeliveryRequest, Integer> {

    List<DeliveryRequest> findByUserId(int user_id);

    List<DeliveryRequest> findByDriverId(int driver_id);

    List<DeliveryRequest> findByUserIdAndDeliveryStatusId(int userId, int deliveryStatusId);
}

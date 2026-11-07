package com.parceldelivery.core.repository;

import com.parceldelivery.core.entity.DeliveryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface DeliveryStatusRepository extends JpaRepository<DeliveryStatus, Long> {
    Optional<DeliveryStatus> findByName(String name);

}

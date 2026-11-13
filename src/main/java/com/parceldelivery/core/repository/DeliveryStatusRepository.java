package com.parceldelivery.core.repository;

import com.parceldelivery.core.entity.DeliveryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.cache.annotation.Cacheable;
import java.util.Optional;

public interface DeliveryStatusRepository extends JpaRepository<DeliveryStatus, Long> {
    @Cacheable("deliveryStatuses")
    Optional<DeliveryStatus> findByName(String name);

}

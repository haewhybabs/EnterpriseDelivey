package com.parceldelivery.core.repository;

import com.parceldelivery.core.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    Optional<Rating> findByDeliveryRequestId(Long deliveryRequestId);
}

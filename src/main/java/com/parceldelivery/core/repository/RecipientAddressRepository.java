package com.parceldelivery.core.repository;

import com.parceldelivery.core.entity.RecipientAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecipientAddressRepository extends JpaRepository<RecipientAddress, Long> {
    List<RecipientAddress> findByUserId(Long userId);
}

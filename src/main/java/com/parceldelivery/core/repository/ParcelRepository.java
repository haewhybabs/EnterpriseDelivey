package com.parceldelivery.core.repository;

import com.parceldelivery.core.entity.Parcel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParcelRepository extends JpaRepository<Parcel, Long> {
    List<Parcel> findByUserId(Long userId);

}
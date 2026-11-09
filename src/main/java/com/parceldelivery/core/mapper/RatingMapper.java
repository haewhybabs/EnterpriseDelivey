package com.parceldelivery.core.mapper;

import com.parceldelivery.core.dto.RatingResponseDTO;
import com.parceldelivery.core.entity.Rating;
import org.springframework.stereotype.Component;

@Component
public class RatingMapper {

    public RatingResponseDTO toResponseDTO(Rating rating) {
        RatingResponseDTO dto = new RatingResponseDTO();
        dto.setId(rating.getId());
        dto.setDeliveryRequestId(rating.getDeliveryRequest().getId());
        dto.setSenderRating(rating.getSenderRating());
        return dto;
    }
}

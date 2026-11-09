package com.parceldelivery.core.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatingResponseDTO {
    private Long id;
    private Long deliveryRequestId;
    private Integer senderRating;
}

package com.parceldelivery.core.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatingRequestDTO {
    private Long deliveryRequestId;
    private Integer senderRating;
}

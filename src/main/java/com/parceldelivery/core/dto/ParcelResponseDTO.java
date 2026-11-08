package com.parceldelivery.core.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParcelResponseDTO {
    private Long id;
    private Long userId;
    private String name;
    private String description;

}

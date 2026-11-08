package com.parceldelivery.core.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParcelRequestDTO {
    private Long userId;
    private String name;
    private String description;

}

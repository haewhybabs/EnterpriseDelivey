package com.parceldelivery.core.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DriverRequestDTO {
    private Long userId;
    private String phoneNumber;
    private String address;

}

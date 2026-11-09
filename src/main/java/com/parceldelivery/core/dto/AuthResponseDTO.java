package com.parceldelivery.core.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponseDTO {
    private String token;
    private String email;
    private String roleName;
}

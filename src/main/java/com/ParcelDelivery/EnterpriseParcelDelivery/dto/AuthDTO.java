package com.ParcelDelivery.EnterpriseParcelDelivery.dto;

import com.ParcelDelivery.EnterpriseParcelDelivery.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthDTO {
    @NotNull
    private String email;
    @NotNull
    private String password;

}

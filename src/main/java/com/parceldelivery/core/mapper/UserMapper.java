package com.parceldelivery.core.mapper;

import org.springframework.stereotype.Component;

import com.parceldelivery.core.dto.UserResponseDTO;
import com.parceldelivery.core.entity.User;

@Component
public class UserMapper {
    public UserResponseDTO toResponseDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRoleName(user.getRole().getName());
        return dto;
    }

}

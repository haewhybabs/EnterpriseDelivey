package com.parceldelivery.core.mapper;

import org.springframework.stereotype.Component;

import com.parceldelivery.core.dto.RoleResponseDTO;
import com.parceldelivery.core.entity.Role;

@Component
public class RoleMapper {
    public RoleResponseDTO toResponseDTO(Role role) {
        RoleResponseDTO dto = new RoleResponseDTO();
        dto.setId(role.getId());
        dto.setName(role.getName());
        return dto;
    }
}

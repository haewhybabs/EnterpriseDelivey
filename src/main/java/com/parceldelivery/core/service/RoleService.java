package com.parceldelivery.core.service;

import com.parceldelivery.core.dto.RoleRequestDTO;
import com.parceldelivery.core.dto.RoleResponseDTO;
import com.parceldelivery.core.entity.Role;
import com.parceldelivery.core.exception.ResourceNotFoundException;
import com.parceldelivery.core.mapper.RoleMapper;
import com.parceldelivery.core.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleResponseDTO createRole(RoleRequestDTO dto) {
        Role role = new Role();
        role.setName(dto.getName());
        Role saved = roleRepository.save(role);
        return roleMapper.toResponseDTO(saved);
    }

    public RoleResponseDTO getRoleById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + id));
        return roleMapper.toResponseDTO(role);
    }

    public List<RoleResponseDTO> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(roleMapper::toResponseDTO).toList();
    }

}

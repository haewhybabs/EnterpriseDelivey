package com.parceldelivery.core.service;

import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.parceldelivery.core.dto.RoleResponseDTO;
import com.parceldelivery.core.entity.Role;
import com.parceldelivery.core.exception.ResourceNotFoundException;
import com.parceldelivery.core.mapper.RoleMapper;
import com.parceldelivery.core.repository.RoleRepository;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {
    @Mock
    private RoleRepository roleRepository;

    @Mock
    private RoleMapper roleMapper;

    @InjectMocks
    private RoleService roleService;

    @Test
    void getRoleById_returnsRole_whenFound() {
        // Arrange
        Role role = new Role();
        role.setId(1L);
        role.setName("Sender");

        RoleResponseDTO expectedDto = new RoleResponseDTO();
        expectedDto.setId(1L);
        expectedDto.setName("Sender");

        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        when(roleMapper.toResponseDTO(role)).thenReturn(expectedDto);

        // Act
        RoleResponseDTO result = roleService.getRoleById(1L);

        // Assert
        assertThat(result.getName()).isEqualTo("Sender");

    }

    @Test
    void getRoleById_throwsResourceNotFound_whenMissing() {
        // Arrange
        when(roleRepository.findById(99L)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(ResourceNotFoundException.class, () -> roleService.getRoleById(99L));
    }

}
package com.parceldelivery.core.service;

import com.parceldelivery.core.dto.UserRequestDTO;
import com.parceldelivery.core.dto.UserResponseDTO;
import com.parceldelivery.core.entity.Role;
import com.parceldelivery.core.entity.User;
import com.parceldelivery.core.exception.BadRequestException;
import com.parceldelivery.core.mapper.UserMapper;
import com.parceldelivery.core.repository.RoleRepository;
import com.parceldelivery.core.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void register_savesHashedPassword_whenEmailAndRoleAreValid() {
        // Arrange
        UserRequestDTO dto = new UserRequestDTO();
        dto.setName("Ada Lovelace");
        dto.setEmail("ada@example.com");
        dto.setPassword("rawPassword123");
        dto.setRoleName("Sender");

        Role role = new Role();
        role.setId(1L);
        role.setName("Sender");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setEmail("ada@example.com");
        savedUser.setRole(role);

        UserResponseDTO expectedDto = new UserResponseDTO();
        expectedDto.setId(1L);
        expectedDto.setEmail("ada@example.com");
        expectedDto.setRoleName("Sender");

        when(userRepository.findByEmail("ada@example.com")).thenReturn(Optional.empty());
        when(roleRepository.findByName("Sender")).thenReturn(Optional.of(role));
        when(passwordEncoder.encode("rawPassword123")).thenReturn("hashed-value");
        when(userRepository.save(org.mockito.ArgumentMatchers.any(User.class))).thenReturn(savedUser);
        when(userMapper.toResponseDTO(savedUser)).thenReturn(expectedDto);

        // Act
        UserResponseDTO result = userService.register(dto);

        // Assert
        assertThat(result.getEmail()).isEqualTo("ada@example.com");

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        assertThat(userCaptor.getValue().getPassword()).isEqualTo("hashed-value");
    }

    @Test
    void register_throwsBadRequest_whenEmailAlreadyInUse() {
        // Arrange
        UserRequestDTO dto = new UserRequestDTO();
        dto.setEmail("ada@example.com");

        when(userRepository.findByEmail("ada@example.com")).thenReturn(Optional.of(new User()));

        // Act + Assert
        assertThrows(BadRequestException.class, () -> userService.register(dto));
    }

    @Test
    void register_throwsBadRequest_whenRoleNameIsInvalid() {
        // Arrange
        UserRequestDTO dto = new UserRequestDTO();
        dto.setEmail("ada@example.com");
        dto.setRoleName("Overlord");

        when(userRepository.findByEmail("ada@example.com")).thenReturn(Optional.empty());
        when(roleRepository.findByName("Overlord")).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(BadRequestException.class, () -> userService.register(dto));
    }
}

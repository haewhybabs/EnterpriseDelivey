package com.parceldelivery.core.service;

import com.parceldelivery.core.exception.BadRequestException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.parceldelivery.core.dto.UserRequestDTO;
import com.parceldelivery.core.dto.UserResponseDTO;
import com.parceldelivery.core.entity.Role;
import com.parceldelivery.core.entity.User;
import com.parceldelivery.core.mapper.UserMapper;
import com.parceldelivery.core.repository.RoleRepository;
import com.parceldelivery.core.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponseDTO register(UserRequestDTO dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new BadRequestException("Email already in use: " + dto.getEmail());
        }
        Role role = roleRepository.findByName(dto.getRoleName())
                .orElseThrow(() -> new BadRequestException("Invalid role: " + dto.getRoleName()));

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(role);

        User saved = userRepository.save(user);
        return userMapper.toResponseDTO(saved);

    }

}

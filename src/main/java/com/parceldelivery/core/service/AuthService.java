package com.parceldelivery.core.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.parceldelivery.core.dto.AuthRequestDTO;
import com.parceldelivery.core.dto.AuthResponseDTO;
import com.parceldelivery.core.entity.User;
import com.parceldelivery.core.exception.ResourceNotFoundException;
import com.parceldelivery.core.repository.UserRepository;
import com.parceldelivery.core.security.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public AuthResponseDTO login(AuthRequestDTO dto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));

        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + dto.getEmail()));

        AuthResponseDTO response = new AuthResponseDTO();
        response.setToken(jwtUtil.generateToken(user.getEmail()));
        response.setEmail(user.getEmail());
        response.setRoleName(user.getRole().getName());
        return response;
    }

}

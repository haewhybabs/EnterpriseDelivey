package com.ParcelDelivery.EnterpriseParcelDelivery.service;

import com.ParcelDelivery.EnterpriseParcelDelivery.advice.BadRequestException;
import com.ParcelDelivery.EnterpriseParcelDelivery.entity.User;
import com.ParcelDelivery.EnterpriseParcelDelivery.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository repository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.findByEmail(email);
        if (user == null) {
            throw new BadRequestException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), List.of(new SimpleGrantedAuthority(user.getRole().getName())));
    }
}

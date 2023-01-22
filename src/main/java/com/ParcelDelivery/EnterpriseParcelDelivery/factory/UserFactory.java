package com.ParcelDelivery.EnterpriseParcelDelivery.factory;

import com.ParcelDelivery.EnterpriseParcelDelivery.dto.UserDTO;
import com.ParcelDelivery.EnterpriseParcelDelivery.entity.Role;
import com.ParcelDelivery.EnterpriseParcelDelivery.entity.User;
import com.ParcelDelivery.EnterpriseParcelDelivery.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserFactory {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    public User createEntity(UserDTO dto,Role role) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        String encryptedPassword = passwordEncoder.encode(dto.getPassword());
        user.setPassword(encryptedPassword);
        user.setRole(role);
        return user;
    }
}

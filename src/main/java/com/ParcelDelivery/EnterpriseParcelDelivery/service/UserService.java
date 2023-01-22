package com.ParcelDelivery.EnterpriseParcelDelivery.service;

import com.ParcelDelivery.EnterpriseParcelDelivery.advice.BadRequestException;
import com.ParcelDelivery.EnterpriseParcelDelivery.dto.UserDTO;
import com.ParcelDelivery.EnterpriseParcelDelivery.entity.Role;
import com.ParcelDelivery.EnterpriseParcelDelivery.entity.User;
import com.ParcelDelivery.EnterpriseParcelDelivery.factory.UserFactory;
import com.ParcelDelivery.EnterpriseParcelDelivery.repository.RoleRepository;
import com.ParcelDelivery.EnterpriseParcelDelivery.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserFactory userFactory;

    public User saveUser(UserDTO userDTO){
        Role role = roleRepository.findById(userDTO.getRole_id()).orElse(null);
        if (role == null) {
            throw new BadRequestException("Role not found with id: " + userDTO.getRole_id());
        }
        User checkUser = repository.findByEmail(userDTO.getEmail());
        if(checkUser!=null){
            throw new BadRequestException("User already exist");
        }
        User user = userFactory.createEntity(userDTO,role);
        return repository.save(user);

    }

    public List<User> getUsers(){

        return repository.findAll();
    }
    public List<User> getUsersByRoleId(int roleId){
        return repository.findByRoleId(roleId);
    }
    public User findUserById(int id){
        User user = repository.findById(id).orElse(null);
        if(user==null){
            throw new BadRequestException("User not found with id: " + id);
        }
        return user;
    }
    public String deleteUser(int id){
        repository.deleteById(id);
        return "User deleted";
    }
    public User updateUser(User user){
        User existingUser = repository.findById(user.getId()).orElse(null);
        existingUser.setName(user.getName());
        return repository.save(existingUser);
    }

    public User authenticatedUser(@AuthenticationPrincipal User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User loggedInUser = repository.findByEmail(userDetails.getUsername());
        return loggedInUser;

    }

    public User findUserByEmail(String email) {
        return repository.findByEmail(email);
    }
}

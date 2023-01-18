package com.ParcelDelivery.EnterpriseParcelDelivery.service;

import com.ParcelDelivery.EnterpriseParcelDelivery.dto.UserDTO;
import com.ParcelDelivery.EnterpriseParcelDelivery.entity.Role;
import com.ParcelDelivery.EnterpriseParcelDelivery.entity.User;
import com.ParcelDelivery.EnterpriseParcelDelivery.factory.UserFactory;
import com.ParcelDelivery.EnterpriseParcelDelivery.repository.RoleRepository;
import com.ParcelDelivery.EnterpriseParcelDelivery.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
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
            throw new EntityNotFoundException("Role not found with id: " + userDTO.getRole_id());
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
            throw new EntityNotFoundException("User not found with id: " + id);
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

}

package com.ParcelDelivery.EnterpriseParcelDelivery.service;

import com.ParcelDelivery.EnterpriseParcelDelivery.advice.BadRequestException;
import com.ParcelDelivery.EnterpriseParcelDelivery.entity.Role;
import com.ParcelDelivery.EnterpriseParcelDelivery.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleRepository repository;
    public Role saveRole(Role role){

        return repository.save(role);
    }
    public List<Role> getRoles(){
        return repository.findAll();
    }
    public Role findRoleById(int id){
        Role role = repository.findById(id).orElse(null);
        if(role==null){
            throw new BadRequestException("Role not found with id"+id);
        }
        return role;
    }
    public Role updateRole(Role role){
        Role existingRole = repository.findById(role.getId()).orElse(null);
        existingRole.setName(role.getName());
        return repository.save(existingRole);
    }
    public String deleteRole(int id){
        repository.deleteById(id);
        return "Role deleted";
    }
}

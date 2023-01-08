package com.ParcelDelivery.EnterpriseParcelDelivery.controller;

import com.ParcelDelivery.EnterpriseParcelDelivery.entity.Role;
import com.ParcelDelivery.EnterpriseParcelDelivery.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RoleController {
    @Autowired
    private RoleService service;
    @PostMapping("/role/add")
    public ResponseEntity<Role> saveRole(@RequestBody Role role){
        return new ResponseEntity<>(service.saveRole(role), HttpStatus.CREATED);
    }
    @GetMapping("/roles")
    public ResponseEntity<List> findAllRoles(){
        return ResponseEntity.ok(service.getRoles());
    }
    @GetMapping("/role/{id}")
    public Role getRoleById(@PathVariable int id){
        return service.findRoleById(id);
    }
    @PutMapping("/role/update")
    public Role updateRole(@RequestBody Role role){
        return service.updateRole(role);
    }
    @DeleteMapping("role/delete")
    public String deleteRole(@PathVariable int id){
        return service.deleteRole(id);
    }

}

package com.ParcelDelivery.EnterpriseParcelDelivery.service;

import com.ParcelDelivery.EnterpriseParcelDelivery.advice.BadRequestException;
import com.ParcelDelivery.EnterpriseParcelDelivery.dto.DeliveryRequestResponseDTO;
import com.ParcelDelivery.EnterpriseParcelDelivery.dto.DriverDTO;
import com.ParcelDelivery.EnterpriseParcelDelivery.dto.UserDTO;
import com.ParcelDelivery.EnterpriseParcelDelivery.entity.DeliveryRequest;
import com.ParcelDelivery.EnterpriseParcelDelivery.entity.Driver;
import com.ParcelDelivery.EnterpriseParcelDelivery.entity.Role;
import com.ParcelDelivery.EnterpriseParcelDelivery.entity.User;
import com.ParcelDelivery.EnterpriseParcelDelivery.factory.DriverFactory;
import com.ParcelDelivery.EnterpriseParcelDelivery.factory.UserFactory;
import com.ParcelDelivery.EnterpriseParcelDelivery.repository.DriverRepository;
import com.ParcelDelivery.EnterpriseParcelDelivery.repository.RoleRepository;
import com.ParcelDelivery.EnterpriseParcelDelivery.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DriverService {
    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserFactory userFactory;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private DriverFactory driverFactory;

    public DriverDTO saveDriver(DriverDTO driverDTO){

        Role role = roleRepository.findById(2).orElse(null);
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(driverDTO.getEmail());
        userDTO.setPassword(driverDTO.getPassword());
        userDTO.setName(driverDTO.getName());
        userDTO.setRole_id(2);

        User newUser = userFactory.createEntity(userDTO,role);
        User user = userRepository.save(newUser);
        Driver driver = driverFactory.createEntity(driverDTO,user);
        Driver newDriver = driverRepository.save(driver);
        return driverFactory.createDriverDTO(newDriver);
    }
    public List<DriverDTO> getDrivers(){
        List<Driver> drivers = driverRepository.findAll();

        List<DriverDTO> dtos = new ArrayList<>();
        for (Driver driver: drivers){
            DriverDTO dto = driverFactory.createDriverDTO(driver);
            dtos.add(dto);
        }
        return dtos;

    }
    public DriverDTO findByDriverId(int id){

        Driver driver = driverRepository.findById(id).orElse(null);
        return driverFactory.createDriverDTO(driver);
    }
    public String deleteDriver(int id){
        driverRepository.deleteById(id);
        return "Driver deleted";
    }
    public Driver updateDriver(Driver driver){
        Driver existingDriver = driverRepository.findById(driver.getId()).get();
        existingDriver.setAddress(driver.getAddress());
        existingDriver.setPhone_number(driver.getPhone_number());
        return driverRepository.save(existingDriver);
    }
}

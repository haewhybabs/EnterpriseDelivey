package com.ParcelDelivery.EnterpriseParcelDelivery.service;

import com.ParcelDelivery.EnterpriseParcelDelivery.advice.BadRequestException;
import com.ParcelDelivery.EnterpriseParcelDelivery.dto.DriverDTO;
import com.ParcelDelivery.EnterpriseParcelDelivery.entity.Driver;
import com.ParcelDelivery.EnterpriseParcelDelivery.entity.User;
import com.ParcelDelivery.EnterpriseParcelDelivery.repository.DriverRepository;
import com.ParcelDelivery.EnterpriseParcelDelivery.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class DriverService {
    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private UserRepository userRepository;

    public Driver saveDriver(DriverDTO driverDTO){
        Driver driver = new Driver();
        driver.setPhone_number(driverDTO.getPhone_number());
        driver.setAddress(driverDTO.getAddress());
        User user = userRepository.findById(driverDTO.getUser_id()).get();
        if(user.getRole().getId() !=2){
            throw new BadRequestException("Only driver can be added");
        }
        if(user==null){
            throw new EntityNotFoundException("User not found with the id"+ driverDTO.getUser_id());
        }
        driver.setUser(user);
        return driverRepository.save(driver);

    }
    public List<Driver> getDrivers(){

        return driverRepository.findAll();
    }
    public Driver findByDriverId(int id){

        return driverRepository.findById(id).orElse(null);
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

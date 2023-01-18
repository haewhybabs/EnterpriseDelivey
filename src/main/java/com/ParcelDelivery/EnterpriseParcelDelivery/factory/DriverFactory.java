package com.ParcelDelivery.EnterpriseParcelDelivery.factory;

import com.ParcelDelivery.EnterpriseParcelDelivery.dto.DriverDTO;
import com.ParcelDelivery.EnterpriseParcelDelivery.entity.Driver;
import com.ParcelDelivery.EnterpriseParcelDelivery.entity.User;
import org.springframework.stereotype.Service;

@Service
public class DriverFactory {

    public Driver createEntity(DriverDTO driverDTO, User user){
        Driver driver = new Driver();
        driver.setUser(user);
        driver.setAddress(driverDTO.getAddress());
        driver.setPhone_number(driverDTO.getPhone_number());
        return driver;
    }
    public DriverDTO createDriverDTO(Driver driver){
        DriverDTO dto = new DriverDTO();
        dto.setUser_id(driver.getUser().getId());
        dto.setName(driver.getUser().getName());
        dto.setEmail(driver.getUser().getEmail());
        dto.setAddress(driver.getAddress());
        dto.setPhone_number(driver.getPhone_number());
        dto.setId(driver.getId());
        return dto;
    }

}

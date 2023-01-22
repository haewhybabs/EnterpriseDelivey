package com.ParcelDelivery.EnterpriseParcelDelivery.controller;

import com.ParcelDelivery.EnterpriseParcelDelivery.dto.ParcelDTO;
import com.ParcelDelivery.EnterpriseParcelDelivery.entity.Parcel;
import com.ParcelDelivery.EnterpriseParcelDelivery.entity.User;
import com.ParcelDelivery.EnterpriseParcelDelivery.service.ParcelService;
import com.ParcelDelivery.EnterpriseParcelDelivery.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ParcelController {
    @Autowired
    private ParcelService service;

    @Autowired
    private UserService userService;

   @PostMapping("/parcel/add")
    public Parcel addParcel(@RequestBody ParcelDTO parcelDTO, @AuthenticationPrincipal User user){
       User loggedInUser = userService.authenticatedUser(user);
       parcelDTO.setUser_id(loggedInUser.getId());
       return service.saveParcel(parcelDTO);
    }
    @GetMapping("/parcels")
    public List<Parcel> findAllParcels(){

       return service.getParcels();
    }
    @GetMapping("/parcel/{id}")
    public Parcel findParcelById(@PathVariable int id){

       return service.getParcelById(id);
    }
    @PutMapping("/parcel/update")
    public Parcel updateParcel(@RequestBody Parcel parcel){

       return service.updateParcel(parcel);
    }
    @DeleteMapping("/parcel/delete/{id}")
    public String deleteParcel(@PathVariable int id){

       return service.deleteParcel(id);
    }


}

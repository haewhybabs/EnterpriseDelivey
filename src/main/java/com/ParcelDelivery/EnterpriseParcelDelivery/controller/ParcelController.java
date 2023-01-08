package com.ParcelDelivery.EnterpriseParcelDelivery.controller;

import com.ParcelDelivery.EnterpriseParcelDelivery.entity.Parcel;
import com.ParcelDelivery.EnterpriseParcelDelivery.service.ParcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ParcelController {
    @Autowired
    private ParcelService service;

   @PostMapping("/parcel/add")
    public Parcel addParcel(@RequestBody Parcel parcel){

       return service.saveParcel(parcel);
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

package com.ParcelDelivery.EnterpriseParcelDelivery.service;

import com.ParcelDelivery.EnterpriseParcelDelivery.advice.BadRequestException;
import com.ParcelDelivery.EnterpriseParcelDelivery.dto.ParcelDTO;
import com.ParcelDelivery.EnterpriseParcelDelivery.entity.Parcel;
import com.ParcelDelivery.EnterpriseParcelDelivery.factory.ParcelFactory;
import com.ParcelDelivery.EnterpriseParcelDelivery.repository.ParcelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParcelService {
    @Autowired
    private ParcelRepository repository;
    @Autowired
    private ParcelFactory parcelFactory;

    public Parcel saveParcel(ParcelDTO parcelDTO){
        Parcel parcel = parcelFactory.createEntity(parcelDTO);
        return repository.save(parcel);
    }

    public List<Parcel> getParcels(){
        return repository.findAll();
    }
    public Parcel getParcelById(int id){
        Parcel parcel = repository.findById(id).orElse(null);
        if (parcel==null){
            throw new BadRequestException("Parcel not found with id: " + id);
        }
        return parcel;
    }

    public Parcel getParcelByName(String name){
        return repository.findByName(name);
    }
    public String deleteParcel(int id){
        repository.deleteById(id);
        return "Parcel removed";
    }
    public Parcel updateParcel(Parcel parcel){
        Parcel existingParcel = repository.findById(parcel.getId()).orElse(null);
        existingParcel.setName(parcel.getName());
        existingParcel.setDescription(parcel.getDescription());
        return repository.save(existingParcel);
    }



}

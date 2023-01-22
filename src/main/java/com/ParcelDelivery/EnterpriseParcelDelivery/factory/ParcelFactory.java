package com.ParcelDelivery.EnterpriseParcelDelivery.factory;

import com.ParcelDelivery.EnterpriseParcelDelivery.dto.ParcelDTO;
import com.ParcelDelivery.EnterpriseParcelDelivery.entity.Parcel;
import org.springframework.stereotype.Service;

@Service
public class ParcelFactory {
    public Parcel createEntity(ParcelDTO dto){
        Parcel parcel = new Parcel();
        parcel.setName(dto.getName());
        parcel.setDescription(dto.getDescription());
        parcel.setUser_id(dto.getUser_id());
        return parcel;
    }

}

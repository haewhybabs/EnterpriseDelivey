package com.ParcelDelivery.EnterpriseParcelDelivery.service;

import com.ParcelDelivery.EnterpriseParcelDelivery.entity.DeliveryStatus;
import com.ParcelDelivery.EnterpriseParcelDelivery.repository.DeliveryStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class DeliveryStatusService {
    @Autowired
    private DeliveryStatusRepository repository;

    public DeliveryStatus saveStatus(DeliveryStatus deliveryStatus){
        return repository.save(deliveryStatus);
    }
    public List<DeliveryStatus> getStatuses(){
        return repository.findAll();
    }
    public DeliveryStatus getStatusById(int id){
        return repository.findById(id).orElse(null);
    }
    public DeliveryStatus updateStatus(DeliveryStatus deliveryStatus){
        DeliveryStatus existingStatus = repository.findById(deliveryStatus.getId()).orElse(null);
        if(existingStatus==null){
            throw new EntityNotFoundException("Status not found");
        }
        existingStatus.setStatus(deliveryStatus.getStatus());
        return repository.save(existingStatus);
    }
    public String deleteStatus(int id){
        repository.deleteById(id);
        return "Status deleted";
    }

}
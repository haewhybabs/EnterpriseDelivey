package com.ParcelDelivery.EnterpriseParcelDelivery.service;

import com.ParcelDelivery.EnterpriseParcelDelivery.advice.BadRequestException;
import com.ParcelDelivery.EnterpriseParcelDelivery.entity.DeliveryStatus;
import com.ParcelDelivery.EnterpriseParcelDelivery.repository.DeliveryStatusRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            throw new BadRequestException("Status not found");
        }
        existingStatus.setStatus(deliveryStatus.getStatus());
        return repository.save(existingStatus);
    }
    public String deleteStatus(int id){
        repository.deleteById(id);
        return "Status deleted";
    }

}

package com.ParcelDelivery.EnterpriseParcelDelivery.service;

import com.ParcelDelivery.EnterpriseParcelDelivery.dto.RatingDTO;
import com.ParcelDelivery.EnterpriseParcelDelivery.entity.DeliveryRequest;
import com.ParcelDelivery.EnterpriseParcelDelivery.entity.Rating;
import com.ParcelDelivery.EnterpriseParcelDelivery.factory.RatingFactory;
import com.ParcelDelivery.EnterpriseParcelDelivery.repository.DeliveryRequestRepository;
import com.ParcelDelivery.EnterpriseParcelDelivery.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class RatingService {
    @Autowired
    private RatingRepository repository;
    @Autowired
    private DeliveryRequestRepository deliveryRequestRepository;

    @Autowired
    private RatingFactory ratingFactory;

    public RatingDTO createRating(RatingDTO ratingDTO){
        DeliveryRequest deliveryRequest =  deliveryRequestRepository.findById(ratingDTO.getDelivery_request_id()).orElse(null);
        if(deliveryRequest==null){
            throw new EntityNotFoundException("Delivery request not found");
        }
        Rating rating = ratingFactory.createEntity(ratingDTO,deliveryRequest);
        repository.save(rating);
        RatingDTO dto = ratingFactory.createDTO(rating);
        return dto;

    }
}

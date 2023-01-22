package com.ParcelDelivery.EnterpriseParcelDelivery.service;

import com.ParcelDelivery.EnterpriseParcelDelivery.advice.BadRequestException;
import com.ParcelDelivery.EnterpriseParcelDelivery.dto.RatingDTO;
import com.ParcelDelivery.EnterpriseParcelDelivery.entity.DeliveryRequest;
import com.ParcelDelivery.EnterpriseParcelDelivery.entity.Rating;
import com.ParcelDelivery.EnterpriseParcelDelivery.factory.RatingFactory;
import com.ParcelDelivery.EnterpriseParcelDelivery.repository.DeliveryRequestRepository;
import com.ParcelDelivery.EnterpriseParcelDelivery.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
            throw new BadRequestException("Delivery request not found");
        }
        Rating rating = ratingFactory.createEntity(ratingDTO,deliveryRequest);
        repository.save(rating);
        RatingDTO dto = ratingFactory.createDTO(rating);
        return dto;

    }
    public RatingDTO findRatingById(int id){

        Rating rating  = repository.findById(id).orElse(null);
        if(rating==null){
            throw new BadRequestException("Rating not found");
        }
        RatingDTO dto = ratingFactory.createDTO(rating);
        return dto;
    }
}

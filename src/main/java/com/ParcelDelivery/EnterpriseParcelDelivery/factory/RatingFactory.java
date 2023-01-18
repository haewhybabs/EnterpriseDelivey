package com.ParcelDelivery.EnterpriseParcelDelivery.factory;


import com.ParcelDelivery.EnterpriseParcelDelivery.dto.RatingDTO;
import com.ParcelDelivery.EnterpriseParcelDelivery.entity.DeliveryRequest;
import com.ParcelDelivery.EnterpriseParcelDelivery.entity.Rating;
import org.springframework.stereotype.Service;

@Service
public class RatingFactory {

    public Rating createEntity(RatingDTO ratingDTO, DeliveryRequest deliveryRequest){
        Rating rating = new Rating();
        rating.setSender_rating(ratingDTO.getSender_rating());
        rating.setDeliveryRequest(deliveryRequest);
        return rating;

    }
    public RatingDTO createDTO(Rating rating){
        RatingDTO dto = new RatingDTO();
        dto.setSender_rating(rating.getSender_rating());
        dto.setId(rating.getId());
        dto.setDelivery_request_id(rating.getDeliveryRequest().getId());
        return dto;
    }

}

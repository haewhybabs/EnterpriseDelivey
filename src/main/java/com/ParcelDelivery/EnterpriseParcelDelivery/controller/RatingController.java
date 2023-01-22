package com.ParcelDelivery.EnterpriseParcelDelivery.controller;

import com.ParcelDelivery.EnterpriseParcelDelivery.dto.RatingDTO;
import com.ParcelDelivery.EnterpriseParcelDelivery.entity.Rating;
import com.ParcelDelivery.EnterpriseParcelDelivery.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RatingController {
    @Autowired
    private RatingService service;
    @PostMapping("/rating/create")
    public RatingDTO createRating(@RequestBody RatingDTO ratingDTO){

        return service.createRating(ratingDTO);
    }
    @GetMapping("rating/view/{rating_id}")
    public RatingDTO viewRatingById(@PathVariable int rating_id){
        return service.findRatingById(rating_id);
    }
}

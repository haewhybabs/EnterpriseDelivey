package com.parceldelivery.core.controller;

import com.parceldelivery.core.dto.RatingRequestDTO;
import com.parceldelivery.core.dto.RatingResponseDTO;
import com.parceldelivery.core.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ratings")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RatingResponseDTO rateDelivery(@RequestBody RatingRequestDTO dto) {
        return ratingService.rateDelivery(dto);
    }
}

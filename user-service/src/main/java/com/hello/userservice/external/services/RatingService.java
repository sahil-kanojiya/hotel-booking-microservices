package com.hello.userservice.external.services;

import com.hello.userservice.entity.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "rating-service")
public interface RatingService {

    @PostMapping("/rating")
    public Rating createRating(Rating rating);
}

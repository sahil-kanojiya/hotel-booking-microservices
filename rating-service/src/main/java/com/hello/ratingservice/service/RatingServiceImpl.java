package com.hello.ratingservice.service;

import com.hello.ratingservice.repository.RatingRepo;
import com.hello.ratingservice.entity.Rating;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RatingServiceImpl implements RatingService {

    private final RatingRepo ratingRepo;

    @Override
    public Rating create(Rating rating) {
        log.info("Saving new rating for user: {} on hotel: {}", rating.getUserId(), rating.getHotelId());
        rating.setRatingId(UUID.randomUUID().toString());
        Rating savedRating = ratingRepo.save(rating);
        log.info("Successfully saved rating with ID: {}", savedRating.getRatingId());
        return savedRating;
    }

    @Override
    public List<Rating> getAll() {
        log.info("Fetching all ratings from database");
        return ratingRepo.findAll();
    }

    @Override
    public List<Rating> getAllRatingByUserId(String userId) {
        log.info("Fetching all ratings for userId: {}", userId);
        return ratingRepo.findByUserId(userId);
    }

    @Override
    public List<Rating> getAllRatingByHotelId(String hotelId) {
        log.info("Fetching all ratings for hotelId: {}", hotelId);
        return ratingRepo.findByHotelId(hotelId);
    }
}

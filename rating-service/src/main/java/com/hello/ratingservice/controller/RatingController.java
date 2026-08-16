package com.hello.ratingservice.controller;

import lombok.RequiredArgsConstructor;

import com.hello.ratingservice.service.RatingService;
import com.hello.ratingservice.entity.Rating;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/rating")
@RequiredArgsConstructor
@Slf4j
public class RatingController {

    private final RatingService ratingService;

    @PreAuthorize("hasAuthority('Admin') || hasAuthority('SCOPE_internal')")
    @PostMapping
    public ResponseEntity<Rating> createRating(@RequestBody Rating rating) {
        log.info("Request to create rating for user: {} on hotel: {}", rating.getUserId(), rating.getHotelId());
        return new ResponseEntity<>(ratingService.create(rating), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('Admin') || hasAuthority('SCOPE_internal')")
    @GetMapping
    public ResponseEntity<List<Rating>> getAll() {
        log.info("Request to fetch all ratings");
        return new ResponseEntity<>(ratingService.getAll(), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('Admin') || hasAuthority('SCOPE_internal')")
    @GetMapping("users/{id}")
    public ResponseEntity<List<Rating>> getRatingsByUserId(@PathVariable String id) {
        log.info("Request to fetch ratings by user ID: {}", id);
        return new ResponseEntity<>(ratingService.getAllRatingByUserId(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('Admin') || hasAuthority('SCOPE_internal')")
    @GetMapping("hotels/{id}")
    public ResponseEntity<List<Rating>> getRatingsByHotelId(@PathVariable String id) {
        log.info("Request to fetch ratings by hotel ID: {}", id);
        return new ResponseEntity<>(ratingService.getAllRatingByHotelId(id), HttpStatus.OK);
    }
}

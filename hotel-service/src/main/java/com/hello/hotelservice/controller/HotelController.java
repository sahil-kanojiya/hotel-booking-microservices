package com.hello.hotelservice.controller;

import com.hello.hotelservice.service.HotelService;
import com.hello.hotelservice.entity.Hotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/hotel")
@RequiredArgsConstructor
@Slf4j
public class HotelController {

    private final HotelService hotelService;

    @PreAuthorize(" hasAuthority('Admin') ")
    @PostMapping
    public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel) {
        log.info("Request to create a new hotel: {}", hotel.getName());
        return new ResponseEntity<>(hotelService.create(hotel), HttpStatus.CREATED);
    }

    @PreAuthorize(" hasAuthority('SCOPE_internal') ")
    @GetMapping("/{hId}")
    public ResponseEntity<Hotel> getUser(@PathVariable String hId) {
        log.info("Request to get hotel by ID: {}", hId);
        return new ResponseEntity<>(hotelService.getHotelById(hId), HttpStatus.OK);
    }

    @PreAuthorize(" hasAuthority('SCOPE_internal') || hasAuthority('Admin') ")
    @GetMapping
    public ResponseEntity<List<Hotel>> getAllusers() {
        log.info("Request to fetch all hotels");
        return new ResponseEntity<>(hotelService.getAll(), HttpStatus.OK);
    }
}



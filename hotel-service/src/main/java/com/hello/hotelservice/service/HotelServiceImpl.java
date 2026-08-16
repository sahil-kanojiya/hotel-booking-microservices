package com.hello.hotelservice.service;

import com.hello.hotelservice.exception.ResourceNotFoundException;
import com.hello.hotelservice.repository.HotelRepo;
import com.hello.hotelservice.entity.Hotel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class HotelServiceImpl implements HotelService {

    private final HotelRepo hotelRepo;

    @Override
    public Hotel create(Hotel hotel) {
        log.info("Saving new hotel: {}", hotel.getName());
        hotel.setId(UUID.randomUUID().toString());
        Hotel savedHotel = hotelRepo.save(hotel);
        log.info("Successfully saved hotel with ID: {}", savedHotel.getId());
        return savedHotel;
    }

    @Override
    public List<Hotel> getAll() {
        log.info("Fetching all hotels from database");
        return hotelRepo.findAll();
    }

    @Override
    public Hotel getHotelById(String hotelId) {
        log.info("Fetching hotel details for hotelId: {}", hotelId);
        return hotelRepo.findById(hotelId).orElseThrow(() -> {
            log.error("Hotel with ID {} not found", hotelId);
            return new ResourceNotFoundException("hotel id (" + hotelId + ") not found on the server");
        });
    }
}

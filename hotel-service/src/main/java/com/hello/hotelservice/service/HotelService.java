package com.hello.hotelservice.service;

import com.hello.hotelservice.entity.Hotel;

import java.util.List;

public interface HotelService {

    Hotel create(Hotel hotel);

    List<Hotel> getAll();

    Hotel getHotelById(String hotelId);
}

package com.hello.userservice.service;

import com.hello.userservice.entity.Rating;
import com.hello.userservice.entity.User;
import com.hello.userservice.exception.ResourceNotFoundException;
import com.hello.userservice.external.services.HotelService;
import com.hello.userservice.repository.UserRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final HotelService hotelService;

    private final UserRepo userRepo;

    private final RestTemplate restTemplate;

    @Override
    public User saveUser(User user) {
        log.info("Saving new user: {}", user.getEmail());
        user.setId(UUID.randomUUID().toString());
        User savedUser = userRepo.save(user);
        log.info("Successfully saved user with ID: {}", savedUser.getId());
        return savedUser;
    }

    @Override
    public List<User> getAllUsers() {
        log.info("Fetching all users from database");
        return userRepo.findAll();
    }

    @Override
    public User getUserByUserId(String userId) {
        log.info("Fetching user details for userId: {}", userId);
        User user = userRepo.findById(userId).orElseThrow(() -> {
            log.error("User with ID {} not found", userId);
            return new ResourceNotFoundException("User With Given Id (" + userId + ") Not Found On Server");
        });

        log.info("Fetching ratings for userId: {}", userId);
        Rating[] ratingListArray = restTemplate.getForObject(("http://rating-service/rating/users/" + userId), Rating[].class);
        List<Rating> ratingList = Arrays.stream(ratingListArray).toList();

        log.info("Found {} ratings for user. Fetching associated hotel details.", ratingList.size());
        ratingList = ratingList.stream().map(rating -> {
            rating.setHotel(hotelService.getHotel(rating.getHotelId()));
            return rating;
        }).collect(Collectors.toList());

        user.setRatingsList(ratingList);
        return user;
    }

    @Override
    public User updateUserByUserId(String userId, User newUser) {
        User user = getUserByUserId(userId);
        if (newUser.getName() != null) {
            user.setName(newUser.getName());
        }
        if (newUser.getEmail() != null) {
            user.setEmail(newUser.getEmail());
        }
        if (newUser.getAbout() != null) {
            user.setAbout(newUser.getAbout());
        }
        return userRepo.save(user);
    }

    @Override
    public void deleteUserById(String userId) {
        User user = getUserByUserId(userId);
        userRepo.delete(user);
    }
}

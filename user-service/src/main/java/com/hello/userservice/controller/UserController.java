package com.hello.userservice.controller;

import lombok.RequiredArgsConstructor;

import com.hello.userservice.entity.User;
import com.hello.userservice.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
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
import java.util.UUID;

@RestController
@RequestMapping("/user")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasAuthority('Admin') || hasAuthority('SCOPE_internal')")
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        log.info("Request to create new user with email: {}", user.getEmail());
        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
    }


    @PreAuthorize("hasAuthority('Admin') || hasAuthority('SCOPE_internal')")
    @GetMapping("/{uid}")
    @CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallback")
    public ResponseEntity<User> getUser(@PathVariable String uid) {
        log.info("Request to get user by ID: {}", uid);
        return new ResponseEntity<>(userService.getUserByUserId(uid), HttpStatus.OK);
    }

    public ResponseEntity<User> UserRateLimiterFallback(String uid, Exception ex) {
        log.warn("Rate limit exceeded for user lookup (ID: {}). Generating dummy response. Reason: {}", uid, ex.getMessage());
        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .name("dummy")
                .about("this dummy user created because some service is down")
                .email("dummy@email.com")
                .build();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    public ResponseEntity<User> ratingHotelRetryFallback(Exception ex) {
        log.warn("Retry limits exhausted. Generating dummy response. Reason: {}", ex.getMessage());
        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .name("dummy")
                .about("this dummy user created because some service is down")
                .email("dummy@email.com")
                .build();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    public ResponseEntity<User> ratingHotelFallback(String userId, Exception ex) {
        log.error("Circuit breaker triggered for userId: {}. Downstream service is down. Falling back to dummy user. Reason: {}", userId, ex.getMessage());
        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .name("dummy")
                .about("this dummy user created because some service is down")
                .email("dummy@email.com")
                .build();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('Admin') || hasAuthority('SCOPE_internal')")
    @GetMapping
    public ResponseEntity<List<User>> getAllusers() {
        log.info("Request to fetch all users");
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }
}

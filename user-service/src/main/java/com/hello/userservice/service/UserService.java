package com.hello.userservice.service;

import com.hello.userservice.entity.User;

import java.util.List;

public interface UserService {

    User saveUser(User user);

    List<User> getAllUsers();

    User getUserByUserId(String userId);

    User updateUserByUserId(String userId, User newUser);

    void deleteUserById(String userId);
}

package com.homework.app.service;

import com.homework.app.model.User;
import com.homework.app.payload.UserPayload;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    public User addUser(UserPayload userPayload, String role);
    public List<User> getAllUsersByRole(String role);
    public Optional<User> getUserById(String id);
    public User updateUser(String id, UserPayload userPayload);
    public User deleteUser(String id);

}

package com.homework.app.service;

import com.homework.app.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    public User addUser(User user, String role);
    public List<User> getAllUsersByRole(String role);
    public Optional<User> getUserById(String id);
    public User updateUser(String id, User user);
    public User deleteUser(String id);

}

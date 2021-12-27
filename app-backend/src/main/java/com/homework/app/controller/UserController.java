package com.homework.app.controller;

import com.homework.app.model.User;
import com.homework.app.payload.UserPayload;
import com.homework.app.service.UserServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @ApiOperation(value = "Retrieve all users", notes = "Retrieve all users, only authorized to teachers")
    @GetMapping("/{role}")
    @PreAuthorize("hasAnyAuthority('teacher')")
    public List<User> getAllUsersByRole(@PathVariable String role){
        return userService.getAllUsersByRole(role);
    }

    @ApiOperation(value = "Retrieve user by id", notes = "Retrieve user by id, only authorized to teachers")
    @GetMapping("/user/{id}")
    @PreAuthorize("hasAnyAuthority('teacher')")
    public Optional<User> getUserById(@PathVariable String id){
        return userService.getUserById(id);
    }

    @ApiOperation(value = "Create new user", notes = "Create new user, only authorized to teachers")
    @PostMapping("/{role}")
    @PreAuthorize("hasAnyAuthority('teacher')")
    public User addUser(@PathVariable String role, @RequestBody UserPayload user){
        return userService.addUser(user, role);
    }

    @ApiOperation(value = "Update user", notes = "Update an existing user, only authorized to teachers")
    @PutMapping("user/{id}")
    @PreAuthorize("hasAnyAuthority('teacher')")
    public User updateUser(@PathVariable String id, @RequestBody UserPayload user){
        return userService.updateUser(id, user);
    }

    @ApiOperation(value = "Delete user", notes = "Delete an existing user, only authorized to teachers")
    @DeleteMapping("user/{id}")
    @PreAuthorize("hasAnyAuthority('teacher')")
    public User deleteUser(@PathVariable String id){
        return userService.deleteUser(id);
    }
}

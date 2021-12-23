package com.homework.app.controller;

import com.homework.app.model.User;
import com.homework.app.service.UserServiceImpl;
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

    @GetMapping("/{role}")
    @PreAuthorize("hasAnyAuthority('teacher')")
    public List<User> getAllUsersByRole(@PathVariable String role){
        return userService.getAllUsersByRole(role);
    }

    @GetMapping("/user/{id}")
    @PreAuthorize("hasAnyAuthority('teacher')")
    public Optional<User> getUserById(@PathVariable String id){
        return userService.getUserById(id);
    }

    @PostMapping("/{role}")
    @PreAuthorize("hasAnyAuthority('teacher')")
    public User addUser(@PathVariable String role, @RequestBody User user){
        return userService.addUser(user, role);
    }

    @PutMapping("user/{id}")
    @PreAuthorize("hasAnyAuthority('teacher')")
    public User updateTeacher(@PathVariable String id, @RequestBody User user){
        return userService.updateUser(id, user);
    }

    @DeleteMapping("user/{id}")
    @PreAuthorize("hasAnyAuthority('teacher')")
    public User deleteUser(@PathVariable String id){
        return userService.deleteUser(id);
    }
}

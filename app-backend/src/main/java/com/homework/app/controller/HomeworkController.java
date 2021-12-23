package com.homework.app.controller;

import com.homework.app.model.Homework;
import com.homework.app.service.HomeworkServiceImpl;
import com.homework.app.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/homework")
public class HomeworkController {

    @Autowired
    HomeworkServiceImpl service;

    @Autowired
    UserServiceImpl userService;

    @PostMapping("/")
    @PreAuthorize("hasAnyAuthority('teacher')")
    public Homework addHomework(@RequestBody Homework homework){
        return service.addHomework(homework);
    }

    @GetMapping("/")
    @PreAuthorize("hasAnyAuthority('teacher', 'student')")
    public List<Homework> getAllHomework(){
        return service.getAllHomeworks();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('teacher', 'student')")
    public Optional<Homework> getHomeworkById(@PathVariable String id){
        return service.getHomeworkById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('teacher')")
    public Homework changeHomeworkById(@RequestBody Homework homework, @PathVariable String id){
        return service.changeHomeworkById(homework, id);
    }

    @PutMapping("/status/{id}/{status}")
    @PreAuthorize("hasAnyAuthority('student')")
    public Homework changeHomeworkStatus(@PathVariable String id, @PathVariable String status){
        return service.changeHomeworkStatus(status, id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('teacher')")
    public String deleteHomework(@PathVariable String id){
        return service.deleteHomework(id);
    }

}

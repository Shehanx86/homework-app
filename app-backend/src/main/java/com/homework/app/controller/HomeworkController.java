package com.homework.app.controller;

import com.homework.app.filter.CustomAuthenticationFilter;
import com.homework.app.model.Homework;
import com.homework.app.service.HomeworkServiceImpl;
import com.homework.app.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import static java.util.Arrays.stream;

@RestController
@RequestMapping("/api")
public class HomeworkController {

    @Autowired
    HomeworkServiceImpl service;

    @Autowired
    UserServiceImpl userService;

    @PostMapping("/homework")
    @PreAuthorize("hasAnyAuthority('teacher')")
    public Homework addHomework(@RequestBody Homework homework){
        return service.addHomework(homework);
    }

    @GetMapping("/homework")
    @PreAuthorize("hasAnyAuthority('teacher', 'student')")
    public List<Homework> getAllHomework(){
        return service.getAllHomeworks();
    }

    @GetMapping("/homework/{id}")
    @PreAuthorize("hasAnyAuthority('teacher', 'student')")
    public Optional<Homework> getHomeworkById(@PathVariable String id){
        return service.getHomeworkById(id);
    }

    @PutMapping("/homework/{id}")
    @PreAuthorize("hasAnyAuthority('teacher')")
    public Homework changeHomeworkById(@RequestBody Homework homework, @PathVariable String id){
        return service.changeHomeworkById(homework, id);
    }

    @PutMapping("/homework/{id}/{status}")
    @PreAuthorize("hasAnyAuthority('student')")
    public Homework changeHomeworkStatus(@PathVariable String id, @PathVariable String status){
        return service.changeHomeworkStatus(status, id);
    }

    @DeleteMapping("/homework/{id}")
    @PreAuthorize("hasAnyAuthority('teacher')")
    public String deleteHomework(@PathVariable String id){
        return service.deleteHomework(id);
    }

}

package com.homework.app.controller;

import com.homework.app.model.Homework;
import com.homework.app.payload.HomeworkPayload;
import com.homework.app.payload.StatusPayload;
import com.homework.app.service.HomeworkServiceImpl;
import com.homework.app.service.UserServiceImpl;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(value = "Create Homework", notes = "Create new homework, only authorized to teachers")
    @PostMapping("/")
    @PreAuthorize("hasAnyAuthority('teacher')")
    public Homework addHomework(@RequestBody HomeworkPayload homework){
        return service.addHomework(homework);
    }

    @ApiOperation(value = "Retrieve all Homework", notes = "Retrieve all Homeworks, only authorized to teachers")
    @GetMapping("/")
    @PreAuthorize("hasAnyAuthority('teacher')")
    public List<Homework> getAllHomework(){
        return service.getAllHomeworks();
    }

    @ApiOperation(value = "Retrieve homework by id", notes = "Retrieve homework by its id, only authorized to teachers")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('teacher')")
    public Optional<Homework> getHomeworkById(@PathVariable String id){
        return service.getHomeworkById(id);
    }

    @ApiOperation(value = "Retrieve homeworks of logged in student", notes = "Retrieve all homeworks of the currently logged in student, only authorized to students")
    @GetMapping("/student")
    @PreAuthorize("hasAnyAuthority('student')")
    public List<Homework> getHomeworksOfLoggedInStudent(){
        return service.getHomeworksOfLoggedInStudent();
    }

    @ApiOperation(value = "Retrieve homeworks by student username", notes = "Retrieve all homeworks by assigned student username")
    @GetMapping("/student/{username}")
    @PreAuthorize("hasAnyAuthority('teacher')")
    public List<Homework> getHomeworkByStudentUsername(@PathVariable String username){
        return service.getHomeworksByStudentUsername(username);
    }

    @ApiOperation(value = "Update homeworks by id", notes = "Update/change homeworks by its id, only authorized to teachers")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('teacher')")
    public Homework changeHomeworkById(@RequestBody HomeworkPayload homework, @PathVariable String id){
        return service.changeHomeworkById(homework, id);
    }

    @ApiOperation(value = "Update homework's status by id", notes = "Update homework's status by its id, only authorized to students")
    @PutMapping("/status/{id}")
    @PreAuthorize("hasAnyAuthority('student')")
    public Homework changeHomeworkStatus(@PathVariable String id, @RequestBody StatusPayload status){
        return service.changeHomeworkStatus(status, id);
    }

    @ApiOperation(value = "Delete homework's status by id", notes = "Delete homework's status by its id, only authorized to teachers")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('teacher')")
    public String deleteHomework(@PathVariable String id){
        return service.deleteHomework(id);
    }

}

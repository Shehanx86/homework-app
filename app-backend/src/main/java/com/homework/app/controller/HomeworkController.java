package com.homework.app.controller;

import com.homework.app.model.Homework;
import com.homework.app.service.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class HomeworkController {

    @Autowired
    ServiceImpl service;

    @PostMapping("/homework")
    public Homework addHomework(@RequestBody Homework homework){
        return service.addHomework(homework);
    }

    @GetMapping("/homework")
    public List<Homework> getAllHomework(){
        return service.getAllHomeworks();
    }

    @GetMapping("/homework/{id}")
    public Optional<Homework> getHomeworkById(@PathVariable String id){
        return service.getHomeworkById(id);
    }

    @PutMapping("/homework/{id}")
    public Homework changeHomeworkById(@RequestBody Homework homework, @PathVariable String id){
        return service.changeHomeworkById(homework, id);
    }

    @DeleteMapping("/homework/{id}")
    public String deleteHomework(@PathVariable String id){
        return service.deleteHomework(id);
    }

}

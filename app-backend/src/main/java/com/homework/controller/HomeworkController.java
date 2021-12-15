package com.homework.controller;

import com.homework.model.Homework;
import com.homework.service.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class HomeworkController {

    @Autowired
    ServiceImpl service;

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @PostMapping("/add/homework")
    public Homework addHomework(@RequestBody Homework homework){
        return service.addHomework(homework);
    }

    @GetMapping("/get/homework")
    public List<Homework> getAllHomework(){
        return service.getAllHomeworks();
    }

    @GetMapping("/get/homework/id/{id}")
    public Homework getHomeworkById(@PathVariable String id){
        return service.getHomeworkById(id);
    }

    @GetMapping("/get/homework/title/{title}")
    public List<Homework> getHomeworkByTitle(@PathVariable String title){
        return service.getHomeworkByTitle(title);
    }

    @PutMapping("/edit/homework/id/{id}")
    public Homework changeHomeworkById(@RequestBody Homework homework, @PathVariable String id){
        return service.changeHomeworkById(homework, id);
    }

    @DeleteMapping("/delete/homework/id/{id}")
    public String deleteHomework(@PathVariable String id){
        return service.deleteHomework(id);
    }


}

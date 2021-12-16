package com.homework.service;

import com.homework.model.Homework;

import java.util.List;
import java.util.Optional;

public interface IService {

    Optional<Homework> getHomeworkById(String id);
    List<Homework> getAllHomeworks();
    Homework addHomework(Homework homework);
    Homework changeHomeworkById(Homework newHomework, String id);
    String deleteHomework(String id);

}

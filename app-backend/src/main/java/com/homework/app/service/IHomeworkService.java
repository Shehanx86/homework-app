package com.homework.app.service;

import com.homework.app.model.Homework;

import java.util.List;
import java.util.Optional;

public interface IHomeworkService {

    Optional<Homework> getHomeworkById(String id);
    List<Homework> getAllHomeworks();
    Homework addHomework(Homework homework);
    Homework changeHomeworkById(Homework newHomework, String id);
    String deleteHomework(String id);

}

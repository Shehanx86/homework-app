package com.homework.app.service;

import com.homework.app.model.Homework;
import com.homework.app.payload.HomeworkPayload;

import java.util.List;
import java.util.Optional;

public interface IHomeworkService {

    Optional<Homework> getHomeworkById(String id);
    List<Homework> getAllHomeworks();
    Homework addHomework(HomeworkPayload homeworkPayload);
    Homework changeHomeworkById(HomeworkPayload homeworkPayload, String id);
    Homework deleteHomework(String id);
    List<Homework> getHomeworksByStudentUsername(String username);
}

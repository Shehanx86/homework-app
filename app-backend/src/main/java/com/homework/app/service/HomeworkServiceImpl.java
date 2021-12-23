package com.homework.app.service;

import com.homework.app.model.Homework;
import com.homework.app.payload.HomeworkPayload;
import com.homework.app.respository.HomeworkRepository;
import com.homework.app.respository.MongoTemplateOperations;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class HomeworkServiceImpl implements IHomeworkService {

    @Autowired
    HomeworkRepository hwRepository;

    @Autowired
    MongoTemplateOperations mongoTemplateOperations;

    public Optional<Homework> getHomeworkById(String id){
        return hwRepository.findById(id);
    }

    public List<Homework> getAllHomeworks(){
        return hwRepository.findAll();
    }

    public Homework addHomework(HomeworkPayload homeworkPayload){
        Homework newHomework = new Homework();
        newHomework.setId(homeworkPayload.getpId());
        newHomework.setTitle(homeworkPayload.getpTitle());
        newHomework.setLastUpdatedAt(homeworkPayload.getpLastUpdatedAt());
        newHomework.setDeadline(homeworkPayload.getpDeadline());
        newHomework.setObjectives(homeworkPayload.getpObjectives());
        newHomework.setAssignedBy(homeworkPayload.getpAssignedBy());
        newHomework.setAssignedTo(homeworkPayload.getpAssignedTo());
        newHomework.setCreatedAt(new Date());

        return hwRepository.save(newHomework);
    }

    public Homework changeHomeworkStatus(String status, String id){
        Optional<Homework> homework= hwRepository.findById(id);
        if(homework.isPresent()){
            Homework updatedHomework = homework.get();
            updatedHomework.setStatus(status);
            return hwRepository.save(updatedHomework);
        } else {
            return null;
        }
    }

    public Homework changeHomeworkById(HomeworkPayload homeworkPayload, String id){

        Homework newHomework = new Homework();
        newHomework.setId(homeworkPayload.getpId());
        newHomework.setTitle(homeworkPayload.getpTitle());
        newHomework.setCreatedAt(homeworkPayload.getpCreatedAt());
        newHomework.setDeadline(homeworkPayload.getpDeadline());
        newHomework.setObjectives(homeworkPayload.getpObjectives());
        newHomework.setAssignedBy(homeworkPayload.getpAssignedBy());
        newHomework.setAssignedTo(homeworkPayload.getpAssignedTo());
        newHomework.setLastUpdatedAt(new Date());

        Optional<Homework> currentHomework = hwRepository.findById(id);

        if(currentHomework.isPresent()){
            newHomework = currentHomework.get();
            newHomework.setId(currentHomework.get().getId());
            newHomework.setLastUpdatedAt(new Date());
            return hwRepository.save(newHomework);
        } else {
            return null;
        }
    }

    public List<Homework> getHomeworksByStudentUsername(String username){
        return mongoTemplateOperations.getHomeworksByStudentUsername(username);
    }

    public String deleteHomework(String id) {
        hwRepository.deleteById(id);
        return "Homework " + id + " deleted";
    }
}


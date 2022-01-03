package com.homework.app.service;

import com.homework.app.model.Homework;
import com.homework.app.model.User;
import com.homework.app.payload.HomeworkPayload;
import com.homework.app.payload.StatusPayload;
import com.homework.app.respository.HomeworkRepository;
import com.homework.app.respository.MongoTemplateOperations;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class HomeworkServiceImpl implements IHomeworkService {

    private final Logger logger = LoggerFactory.getLogger(HomeworkServiceImpl.class);

    @Autowired
    private HomeworkRepository hwRepository;

    @Autowired
    private MongoTemplateOperations mongoTemplateOperations;

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
        newHomework.setLastUpdatedAt(new Date());
        newHomework.setDeadline(homeworkPayload.getpDeadline());
        newHomework.setObjectives(homeworkPayload.getpObjectives());
        newHomework.setAssignedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        newHomework.setAssignedTo(homeworkPayload.getpAssignedTo());
        newHomework.setCreatedAt(new Date());
        newHomework.setStatus("Not Finished");
        logger.info("Homework added successfully.");
        return hwRepository.save(newHomework);
    }

    public Homework changeHomeworkStatus(StatusPayload status, String id){
        Optional<Homework> homework= hwRepository.findById(id);
        if(homework.isPresent()){
            Homework updatedHomework = homework.get();
            updatedHomework.setStatus(status.getStatus());
            logger.info("Homework's status changed successfully.");
            return hwRepository.save(updatedHomework);
        } else {
            logger.info("No homework found with the provided ID.");
            return null;
        }
    }

    public Homework changeHomeworkById(HomeworkPayload homeworkPayload, String id){

        Homework newHomework = new Homework();
        newHomework.setTitle(homeworkPayload.getpTitle());
        newHomework.setCreatedAt(homeworkPayload.getpCreatedAt());
        newHomework.setDeadline(homeworkPayload.getpDeadline());
        newHomework.setObjectives(homeworkPayload.getpObjectives());
        newHomework.setAssignedBy(homeworkPayload.getpAssignedBy());
        newHomework.setAssignedTo(homeworkPayload.getpAssignedTo());

        Optional<Homework> currentHomework = hwRepository.findById(id);

        if(currentHomework.isPresent()){
            Homework UpdatingHomework = currentHomework.get();
            UpdatingHomework.setLastUpdatedAt(new Date());

            if (newHomework.getTitle() != null){
                UpdatingHomework.setTitle(newHomework.getTitle());
            }
            if (newHomework.getDeadline() != null){
                UpdatingHomework.setDeadline(newHomework.getDeadline());
            }
            if (newHomework.getObjectives() != null){
                UpdatingHomework.setObjectives(newHomework.getObjectives());
            }
            if (newHomework.getAssignedBy() != null){
                UpdatingHomework.setAssignedBy(newHomework.getAssignedBy());
            }
            if (newHomework.getAssignedTo() != null){
                UpdatingHomework.setAssignedTo(newHomework.getAssignedTo());
            }
            logger.info("Homework changed successfully.");
            return hwRepository.save(UpdatingHomework);
        } else {
            logger.info("No homework found with the provided ID.");
            return null;
        }
    }

    public List<Homework> getHomeworksByStudentUsername(String username){
        return mongoTemplateOperations.getHomeworksByStudentUsername(username);
    }

    public List<Homework> getHomeworksOfLoggedInStudent(){
        return mongoTemplateOperations.getHomeworksOfLoggedInStudent();
    }

    public List<Homework> getHomeworksOfLoggedInTeacher(){
        return mongoTemplateOperations.getHomeworksOfLoggedInTeacher();
    }

    public Homework deleteHomework(String id) {
        Optional<Homework> optionalHomework = hwRepository.findById(id);
        if (optionalHomework.isPresent()){
            Homework deletedHomework = optionalHomework.get();
            hwRepository.delete(deletedHomework);
            logger.info("homework deleted.");
            return deletedHomework;
        } else {
            logger.info("homework was not found");
            return null;
        }
    }
}


package com.homework.app.service;

import com.homework.app.model.Homework;
import com.homework.app.payload.HomeworkPayload;
import com.homework.app.respository.HomeworkRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@NoArgsConstructor
public class HomeworkServiceImpl implements IHomeworkService {

    @Autowired
    HomeworkRepository hwRepository;

    HomeworkServiceImpl(HomeworkRepository hwRepository){
        this.hwRepository = hwRepository;
    }

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
        newHomework.setStatus(homeworkPayload.getpStatus());
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
        newHomework.setLastUpdatedAt(homeworkPayload.getpLastUpdatedAt());
        newHomework.setDeadline(homeworkPayload.getpDeadline());
        newHomework.setObjectives(homeworkPayload.getpObjectives());
        newHomework.setStatus(homeworkPayload.getpStatus());
        newHomework.setCreatedAt(new Date());

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

    public String deleteHomework(String id) {
        hwRepository.deleteById(id);
        return "Homework " + id + " deleted";
    }

}


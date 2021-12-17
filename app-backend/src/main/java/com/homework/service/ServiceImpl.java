package com.homework.service;

import com.homework.model.Homework;
import com.homework.respository.HomeworkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceImpl implements IService{

    HomeworkRepository hwRepository;

    @Autowired
    ServiceImpl(HomeworkRepository hwRepository){
        this.hwRepository = hwRepository;
    }

    public Optional<Homework> getHomeworkById(String id){
        return hwRepository.findById(id);
    }

    public List<Homework> getAllHomeworks(){
        return hwRepository.findAll();
    }

    public Homework addHomework(Homework homework){
        homework.setCreatedAt(new Date());
        return hwRepository.save(homework);
    }

    public Homework changeHomeworkById(Homework newHomework, String id){

        Optional<Homework> currentTomework = hwRepository.findById(id);
        Homework updatedHomework;

        if(currentTomework.isPresent()){
            updatedHomework = currentTomework.get();
            updatedHomework.setLastUpdatedAt(new Date());

            if(newHomework.getTitle() != null){
                updatedHomework.setTitle(newHomework.getTitle());
            }
            if(newHomework.getDeadline() != null){
                updatedHomework.setDeadline(newHomework.getDeadline());
            }
            if(newHomework.getObjectives() != null){
                updatedHomework.setObjectives(newHomework.getObjectives());
            }

            return hwRepository.save(updatedHomework);
        } else {
            return null;
        }
    }

    public String deleteHomework(String id) {
        hwRepository.deleteById(id);
        return "Homework " + id + " deleted";
    }

}


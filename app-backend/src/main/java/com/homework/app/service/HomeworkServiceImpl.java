package com.homework.app.service;

import com.homework.app.model.Homework;
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

    public Homework addHomework(Homework homework){
        homework.setCreatedAt(new Date());
        return hwRepository.save(homework);
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

    public Homework changeHomeworkById(Homework newHomework, String id){

        Optional<Homework> currentTomework = hwRepository.findById(id);
        Homework updatedHomework;

        if(currentTomework.isPresent()){
            updatedHomework = currentTomework.get();
            updatedHomework.setId(currentTomework.get().getId());
            updatedHomework.setLastUpdatedAt(new Date());
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


package com.homework.service;

import com.homework.model.Homework;
import com.homework.respository.HomeworkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ServiceImpl {

    @Autowired
    HomeworkRepository hwRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    public Homework getHomeworkById(String id){
        try {
            return hwRepository.findById(id).get();
        } catch(Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<Homework> getAllHomeworks(){
        try {
            return hwRepository.findAll();
        } catch(Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Homework addHomework(Homework homework){
        homework.setGivenDate(new Date());
        try {
            return hwRepository.save(homework);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<Homework> getHomeworkByTitle(String title){
        try{
            AggregationOperation match = Aggregation.match(Criteria.where("title").is(title));
            AggregationOperation sort = Aggregation.sort(Sort.Direction.ASC, "givenDate");

            Aggregation aggregation = Aggregation.newAggregation(match, sort);

            return mongoTemplate.aggregate(aggregation, mongoTemplate.getCollectionName(Homework.class), Homework.class).getMappedResults();
        } catch(Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Homework changeHomeworkById(Homework newHomework, String id){
        Query query = new Query();
        Update update = new Update();

        try{
            query.addCriteria(Criteria.where("id").is(id));
            if(newHomework.getTitle() != null){
                update.set("title", newHomework.getTitle());
            }
            if(newHomework.getDeadline() != null){
                update.set("deadline", newHomework.getDeadline());
            }
            if(newHomework.getObjectives() != null){
                update.set("objectives", newHomework.getObjectives());
            }

            return mongoTemplate.findAndModify(query, update, Homework.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public String deleteHomework(String id){
        try{
            hwRepository.deleteById(id);
            return "Homowrok " + id +" deleted";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Something went wrong "+ e.getMessage();
        }
    }

}


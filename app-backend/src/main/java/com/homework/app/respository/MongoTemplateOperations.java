package com.homework.app.respository;

import com.homework.app.model.Homework;
import com.homework.app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MongoTemplateOperations {

    private MongoTemplate mongoTemplate;

    @Autowired
    public MongoTemplateOperations(MongoTemplate mongoTemplate){
        this.mongoTemplate = mongoTemplate;
    }

    public User getUserByUsername(String username){
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(username));

        return mongoTemplate.findOne(query, User.class);
    }

    public List<User> getAllUsersByRole(String role){
        Query query = new Query();
        query.addCriteria(Criteria.where("role").is(role));
        return mongoTemplate.find(query, User.class);
    }

    public List<Homework> getHomeworksByStudentUsername(String studentUsername){
        Query query = new Query();
        query.addCriteria(Criteria.where("assignedTo").is(studentUsername));
        return mongoTemplate.find(query, Homework.class);
    }

    public List<Homework> getHomeworksOfLoggedInStudent(){
        Query query = new Query();
        query.addCriteria(Criteria.where("assignedTo").is(SecurityContextHolder.getContext().getAuthentication().getName()));
        return mongoTemplate.find(query, Homework.class);
    }

}

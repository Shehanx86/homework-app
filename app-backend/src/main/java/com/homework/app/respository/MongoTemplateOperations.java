package com.homework.app.respository;

import com.homework.app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MongoTemplateOperations {

    @Autowired
    private MongoTemplate mongoTemplate;

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

}

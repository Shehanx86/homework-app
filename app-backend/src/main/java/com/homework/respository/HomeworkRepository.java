package com.homework.respository;

import com.homework.model.Homework;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HomeworkRepository extends MongoRepository<Homework, String> {

}

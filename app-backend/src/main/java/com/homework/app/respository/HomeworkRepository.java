package com.homework.app.respository;

import com.homework.app.model.Homework;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HomeworkRepository extends MongoRepository<Homework, String> {
}

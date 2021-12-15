package repository;

import Model.Homework;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HomeworkRepository extends MongoRepository<Homework, String> {
}

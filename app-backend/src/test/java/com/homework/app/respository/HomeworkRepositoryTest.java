package com.homework.app.respository;

import com.homework.app.model.Homework;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class HomeworkRepositoryTest {

    @Autowired
    private HomeworkRepository repositoryTest;

    private Homework homework_1;

    @BeforeEach
    void setUp(){
        homework_1 = new Homework(
                "test1",
                "title_1",
                "objective_1",
                new Date(),
                new Date(),
                new Date());

        repositoryTest.save(homework_1);
    }

    @AfterEach
    void tearDown(){
        repositoryTest.deleteById("test1");
    }

    @Test
    @DisplayName("This tests if repository save method is working")
    void saveHomeworkTest() {
        assertEquals(homework_1, repositoryTest.save(homework_1));
    }

    @Test
    @DisplayName("This tests if repository findAll method is working")
    void getAllHomeworksTest() {
        assertFalse((repositoryTest.findAll()).isEmpty());
    }

    @Test
    @DisplayName("This tests if findById works as expected")
    void getHomeworkByIdTest() {
        assertEquals(Optional.of(homework_1), repositoryTest.findById("test1"));
    }
}
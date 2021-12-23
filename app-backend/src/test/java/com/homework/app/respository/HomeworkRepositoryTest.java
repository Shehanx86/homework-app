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
                "status",
                new Date(),
                new Date());

        repositoryTest.save(homework_1);
    }

    @AfterEach
    void tearDown(){
        repositoryTest.deleteById("test1");
    }

    @Test
    @DisplayName("This tests if HomeworkRepository save method is working")
    void saveHomeworkTest() {
        assertEquals(homework_1, repositoryTest.save(homework_1));
    }
    @Test
    @DisplayName("This tests if HomeworkRepository findAll method is working")
    void findAllHomeworksTest() {
        assertFalse((repositoryTest.findAll()).isEmpty());
    }

    @Test
    @DisplayName("This tests if HomeworkRepository findById works as expected")
    void findHomeworkByIdTest() {
        assertNotNull(repositoryTest.findById("test1"));
    }

    @Test
    @DisplayName("This tests if HomeworkRepository deleteById works as expected")
    void deleteHomeworkById() {
        repositoryTest.deleteById("test_id");
        assertNull(repositoryTest.findById("test_id").orElse(null));
    }
}
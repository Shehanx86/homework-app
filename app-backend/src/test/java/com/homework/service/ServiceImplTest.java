package com.homework.service;

import com.homework.app.AppApplication;
import com.homework.model.Homework;
import com.homework.respository.HomeworkRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@ContextConfiguration(classes = AppApplication.class)
class ServiceImplTest {

    @Autowired
    HomeworkRepository repositoryTest;

    Homework homework_1 = new Homework(
            "1",
            "title_1",
            "objective_1",
            new Date(),
            new Date());

    @BeforeEach
    void setUp() {
        repositoryTest.save(homework_1);
    }

    @AfterEach
    void tearDown(){
        repositoryTest.deleteAll();
    }

    @Test
    @DisplayName("This tests if homework is created")
    void saveHomeworkTest() {
        assertEquals(repositoryTest.save(homework_1), homework_1);
    }

    @Test
    @DisplayName("This tests if homework were saved")
    void getAllHomeworksTest() {
        assertFalse((repositoryTest.findAll()).isEmpty());
    }

    @Test
    @DisplayName("This tests if findById works as expected")
    void getHomeworkByIdTest(){
        assertAll("Testing fields in object",
                () -> assertEquals(homework_1.getTitle(), repositoryTest.findById("1").get().getTitle()),
                () -> assertEquals(homework_1.getDeadline(), repositoryTest.findById("1").get().getDeadline()),
                () -> assertEquals(homework_1.getObjectives(), repositoryTest.findById("1").get().getObjectives()),
                () -> assertEquals(homework_1.getGivenDate(), repositoryTest.findById("1").get().getGivenDate()));
    }

    @Test
    @DisplayName("This tests if homework is deleted by id")
    void deleteHomeworkById(){
        repositoryTest.deleteById("1");
        assertTrue((repositoryTest.findAll()).isEmpty());
    }
}
package com.homework.app.respository;

import com.homework.app.model.Homework;
import com.homework.app.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
class MongoTemplateOperationsTest {

    private User user;
    private Homework homework;
    private MongoTemplateOperations mongoTemplateOperations;
    @Mock
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId("test_id");
        user.setName("test_name");
        user.setUsername("test_username");
        user.setPassword("test_password");
        user.setRole("teacher");

        mongoTemplateOperations = new MongoTemplateOperations(mongoTemplate);

        homework = new Homework();
        homework.setId("test_id");
    }

    @Test
    @DisplayName("This tests getting user by username")
    void getUserByUsernameTest() {
        doReturn(user).when(mongoTemplate).findOne(any(Query.class), eq(User.class));
        assertEquals(user, mongoTemplateOperations.getUserByUsername("test_username"));
    }

    @Test
    @DisplayName("This tests getting users by roles")
    void getAllUsersByRoleTest() {
        doReturn(Arrays.asList(user)).when(mongoTemplate).find(any(Query.class), eq(User.class));
        assertEquals(Arrays.asList(user), mongoTemplateOperations.getAllUsersByRole("test_role"));
    }

    @Test
    @DisplayName("This tests getting homework by student username")
    void getHomeworkByStudentUsernameTest() {
        doReturn(Arrays.asList(homework)).when(mongoTemplate).find(any(Query.class), eq(Homework.class));
        assertEquals(Arrays.asList(homework), mongoTemplateOperations.getHomeworksByStudentUsername("test_username"));
    }

}
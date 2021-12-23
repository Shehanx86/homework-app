package com.homework.app.respository;

import com.homework.app.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
class MongoTemplateOperationsTest {

    private User user;
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

}
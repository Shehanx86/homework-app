package com.homework.app.respository;

import com.homework.app.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class UserRepositoryTest {

    @Autowired
    private UserRepository repositoryTest;

    private User user;

    @BeforeEach
    void setUp(){
        user = new User();
        user.setId("test_id");
        user.setName("test_name");
        user.setUsername("test_username");
        user.setPassword("test_password");
        user.setRole("teacher");
        repositoryTest.save(user);
    }

    @AfterEach
    void tearDown(){
        repositoryTest.deleteById("test1");
    }

    @Test
    @DisplayName("This tests if UserRepository save method is working as expected")
    void saveUserTest() {
        assertEquals(user, repositoryTest.save(user));
    }

    @Test
    @DisplayName("This tests if UserRepository findAll method is working as expected")
    void findAllUsersTest() {
        assertFalse((repositoryTest.findAll()).isEmpty());
    }

    @Test
    @DisplayName("This tests if UserRepository findById works as expected")
    void findUsersByIdTest() {
        assertEquals(Optional.of(user), repositoryTest.findById("test_id"));
    }

    @Test
    @DisplayName("This tests if UserRepository deleteById works as expected")
    void deleteUserById() {
        repositoryTest.deleteById("test_id");
        assertNull(repositoryTest.findById("test_id").orElse(null));
    }
}
package com.homework.app.service;

import com.homework.app.model.User;
import com.homework.app.respository.MongoTemplateOperations;
import com.homework.app.respository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@DataMongoTest
class UserServiceImplTest {

    private UserServiceImpl service;
    private User user;

    @Mock
    private UserRepository userRepositoryTest;
    @Mock
    private MongoTemplateOperations mongoTemplateOperationsTest;
    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp(){
        service = new UserServiceImpl(userRepositoryTest, mongoTemplateOperationsTest, passwordEncoder);

        user = new User();
        user.setId("test_id");
        user.setName("test_name");
        user.setUsername("test_username");
        user.setPassword("test_password");
        user.setRole("teacher");
    }

    @Test
    @DisplayName("This tests add user")
    void addUserTest() {
        doReturn(user).when(userRepositoryTest).save(any(User.class));
        assertEquals(user, service.addUser(user, "teacher"));
    }

    @Test
    @DisplayName("This tests get all users by role")
    void getAllUsersByRoleTest() {
        doReturn(Arrays.asList(user)).when(mongoTemplateOperationsTest).getAllUsersByRole(any(String.class));
        assertEquals(Arrays.asList(user), service.getAllUsersByRole("teacher"));
    }

    @Test
    @DisplayName("This tests get user by id")
    void getUserByIdTest() {
        doReturn(Optional.of(user)).when(userRepositoryTest).findById(any(String.class));
        assertEquals(Optional.of(user), service.getUserById("test_id"));
    }

    @Test
    @DisplayName("This tests get user by username")
    void getUserByUsernameTest() {
        doReturn(user).when(mongoTemplateOperationsTest).getUserByUsername(any(String.class));
        assertEquals(user, service.getUserByUsername("test_username"));
    }

    @Test
    @DisplayName("This tests update user")
    void updateUserTest() {
        doReturn(Optional.of(user)).when(userRepositoryTest).findById(any(String.class));
        doReturn(user).when(userRepositoryTest).save(any(User.class));
        assertEquals(user, service.updateUser("test_id", user));
    }

    @Test
    @DisplayName("This tests delete user")
    void deleteUserTest() {
        doReturn(Optional.of(user)).when(userRepositoryTest).findById(any(String.class));
        assertEquals(user, service.deleteUser("test_id"));
    }
}
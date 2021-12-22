package com.homework.app.controller;

import com.homework.app.AppApplication;
import com.homework.app.model.User;
import com.homework.app.service.HomeworkServiceImpl;
import com.homework.app.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Date;

import static com.homework.app.controller.HomeworkControllerTest.asJsonString;
import static com.homework.app.util.UtilJWT.CLAIM;
import static com.homework.app.util.UtilJWT.createToken;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    String access_token_role_teacher = createToken(
            "user_test",
            new Date(System.currentTimeMillis() + 30 * 10 * 1000),
            "test_issuer",
            CLAIM,
            Arrays.asList("teacher")
    );

    String access_token_role_student = createToken(
            "user_test",
            new Date(System.currentTimeMillis() + 30 * 10 * 1000),
            "test_issuer",
            CLAIM,
            Arrays.asList("student")
    );

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl service;

    private User user;

    @BeforeEach
    void setUp() {

        user = new User();
        user.setId("test_id");
        user.setName("test_name");
        user.setUsername("test_username");
        user.setPassword("test_password");
        user.setRole("teacher");
    }

    @Test
    @DisplayName("This tests get all teachers by role teacher")
    void getAllTeachersByRoleTeacherTest() throws Exception {
        doReturn(Arrays.asList(user)).when(service).getAllUsersByRole("teacher");

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/users/teacher")
                .header("Authorization", "Bearer "+ access_token_role_teacher))
                .andExpect(status().is(200))
                .andExpect(content().json("[{}]"));
    }

    @Test
    @DisplayName("This tests get all teachers by role student")
    void getAllTeachersByRoleStudentTest() throws Exception {
        doReturn(Arrays.asList(user)).when(service).getAllUsersByRole("teacher");

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/users/teacher")
                        .header("Authorization", "Bearer "+ access_token_role_student))
                .andExpect(status().is(403));
    }

    @Test
    @DisplayName("This tests add teachers by role teacher")
    void addTeacherByRoleTeacher() throws Exception {

        doReturn(user).when(service).addUser(user, "teacher");

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/users/teacher")
                        .header("Authorization", "Bearer "+ access_token_role_teacher)
                        .content(asJsonString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(content().json("{}"));
    }

    @Test
    @DisplayName("This tests add teachers by role student")
    void addTeacherByRoleStudent() throws Exception {

        doReturn(user).when(service).addUser(user, "teacher");

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/users/teacher")
                        .header("Authorization", "Bearer "+ access_token_role_student)
                        .content(asJsonString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(403));
    }

    @Test
    @DisplayName("This tests change teacher for role teacher")
    void changeTeacherRoleTeacherTest() throws Exception {

        doReturn(user).when(service).updateUser("test_id", user);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/user/test_id")
                        .header("Authorization", "Bearer "+ access_token_role_teacher)
                        .content(asJsonString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(content().json("{}"));
    }

    @Test
    @DisplayName("This tests change teacher for role student")
    void changeTeacherRoleStudentTest() throws Exception {

        doReturn(user).when(service).updateUser("test_id", user);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/user/test_id")
                        .header("Authorization", "Bearer "+ access_token_role_student)
                        .content(asJsonString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(403));
    }

    @Test
    @DisplayName("This tests delete teacher for role teacher")
    void deleteTeacherRolesTeacherTest() throws Exception {

        doReturn(user).when(service).deleteUser("test_id");

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/user/test_id")
                        .header("Authorization", "Bearer "+ access_token_role_teacher))
                .andExpect(content().json("{}"))
                .andExpect(status().is(200));
    }

    @Test
    @DisplayName("This tests delete teacher for role teacher")
    void deleteTeacherRolesStudentTest() throws Exception {

        doReturn(user).when(service).deleteUser("test_id");

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/user/test_id")
                        .header("Authorization", "Bearer "+ access_token_role_student))
                .andExpect(status().is(403));
    }
}
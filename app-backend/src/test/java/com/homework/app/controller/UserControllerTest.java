package com.homework.app.controller;

import com.homework.app.model.User;
import com.homework.app.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Date;

import static com.homework.app.controller.HomeworkControllerTest.asJsonString;
import static com.homework.app.util.UtilJWT.CLAIM;
import static com.homework.app.util.UtilJWT.createToken;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static javax.servlet.http.HttpServletResponse.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    private String access_token_role_teacher = createToken(
            "user_test",
            new Date(System.currentTimeMillis() + 30 * 10 * 1000),
            "test_issuer",
            CLAIM,
            Arrays.asList("teacher")
    );

    private String access_token_role_student = createToken(
            "user_test",
            new Date(System.currentTimeMillis() + 30 * 10 * 1000),
            "test_issuer",
            CLAIM,
            Arrays.asList("student")
    );
    private User user;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl service;

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
    @DisplayName("This tests get all teachers with role teacher")
    void getAllTeachersByRoleTeacherTest() throws Exception {

        doReturn(Arrays.asList(user)).when(service).getAllUsersByRole("teacher");
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/users/teacher")
                .header("Authorization", "Bearer "+ access_token_role_teacher))
                .andExpect(status().is(SC_OK))
                .andExpect(content().json("[{}]"));
    }

    @Test
    @DisplayName("This tests get all teachers with role student")
    void getAllTeachersByRoleStudentTest() throws Exception {

        doReturn(Arrays.asList(user)).when(service).getAllUsersByRole("teacher");
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/users/teacher")
                        .header("Authorization", "Bearer "+ access_token_role_student))
                .andExpect(status().is(SC_FORBIDDEN));
    }

    @Test
    @DisplayName("This tests add teachers with role teacher")
    void addTeacherByRoleTeacher() throws Exception {

        doReturn(user).when(service).addUser(user, "teacher");
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/users/teacher")
                        .header("Authorization", "Bearer "+ access_token_role_teacher)
                        .content(asJsonString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(SC_OK))
                .andExpect(content().json("{}"));
    }

    @Test
    @DisplayName("This tests add teachers with role student")
    void addTeacherByRoleStudent() throws Exception {

        doReturn(user).when(service).addUser(user, "teacher");
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/users/teacher")
                        .header("Authorization", "Bearer "+ access_token_role_student)
                        .content(asJsonString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(SC_FORBIDDEN));
    }

    @Test
    @DisplayName("This tests change teacher with role teacher")
    void changeTeacherByRoleTeacherTest() throws Exception {

        doReturn(user).when(service).updateUser("test_id", user);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/users/user/test_id")
                        .header("Authorization", "Bearer "+ access_token_role_teacher)
                        .content(asJsonString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(SC_OK))
                .andExpect(content().json("{}"));
    }

    @Test
    @DisplayName("This tests change teacher with role student")
    void changeTeacherByRoleStudentTest() throws Exception {

        doReturn(user).when(service).updateUser("test_id", user);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/users/user/test_id")
                        .header("Authorization", "Bearer "+ access_token_role_student)
                        .content(asJsonString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(SC_FORBIDDEN));
    }

    @Test
    @DisplayName("This tests delete teacher with role teacher")
    void deleteTeacherByRolesTeacherTest() throws Exception {

        doReturn(user).when(service).deleteUser("test_id");

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/users/user/test_id")
                        .header("Authorization", "Bearer "+ access_token_role_teacher))
                .andExpect(content().json("{}"))
                .andExpect(status().is(SC_OK));
    }

    @Test
    @DisplayName("This tests delete teacher with role teacher")
    void deleteTeacherByrRolesStudentTest() throws Exception {

        doReturn(user).when(service).deleteUser("test_id");
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/users/user/test_id")
                        .header("Authorization", "Bearer "+ access_token_role_student))
                .andExpect(status().is(SC_FORBIDDEN));
    }
}
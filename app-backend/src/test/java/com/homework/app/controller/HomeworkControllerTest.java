package com.homework.app.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.homework.app.AppApplication;
import com.homework.app.model.Homework;
import com.homework.app.model.User;
import com.homework.app.respository.MongoTemplateOperations;
import com.homework.app.service.HomeworkServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import static com.homework.app.model.Roles.STUDENT;
import static com.homework.app.model.Roles.TEACHER;
import static com.homework.app.util.UtilJWT.CLAIM;
import static com.homework.app.util.UtilJWT.createToken;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class HomeworkControllerTest {

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
    private HomeworkServiceImpl service;

    private Homework homework;

    @BeforeEach
    void setUp(){
        homework = new Homework();
        homework.setId("test_id");
        homework.setTitle("test_title");
        homework.setObjectives("test_objectives");
    }

    @Test
    @DisplayName("This tests api/homework get request for role student")
    void getAllHomeworkRoleTeacherTest() throws Exception {

        doReturn(Arrays.asList(homework)).when(service).getAllHomeworks();

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/homework/")
                    .header("Authorization", "Bearer "+ access_token_role_student))
                    .andExpect(status().is(200));
    }

    @Test
    @DisplayName("This tests api/homework get request for role student")
    void getAllHomeworkRoleStudentTest() throws Exception {

        doReturn(Arrays.asList(homework)).when(service).getAllHomeworks();

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/homework/")
                        .header("Authorization", "Bearer "+ access_token_role_student))
                .andExpect(status().is(200));
    }

    @Test
    @DisplayName("This tests api/homework/id get request for role teacher")
    void getHomeworkByIdRoleTeacherTest() throws Exception {

        doReturn(Optional.of(homework)).when(service).getHomeworkById("test_id");

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/homework/test_id")
                .header("Authorization", "Bearer "+ access_token_role_teacher))
                .andExpect(content().json("{}"))
                .andExpect(status().is(200));
    }

    @Test
    @DisplayName("This tests api/homework/id get request for role student")
    void getHomeworkByIdRoleStudentTest() throws Exception {

        doReturn(Optional.of(homework)).when(service).getHomeworkById("test_id");

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/homework/test_id")
                        .header("Authorization", "Bearer "+ access_token_role_student))
                .andExpect(content().json("{}"))
                .andExpect(status().is(200));
    }

    @Test
    @DisplayName("This tests api/homework/id post request for role teacher")
    void addHomeworkRoleTeacherTest() throws Exception {

        doReturn(homework).when(service).addHomework(homework);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/homework/")
                        .header("Authorization", "Bearer "+ access_token_role_teacher)
                        .content(asJsonString(homework))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(content().json("{}"));
    }

    @Test
    @DisplayName("This tests api/homework/id post request for role student")
    void addHomeworkRoleStudentTest() throws Exception {

        doReturn(homework).when(service).addHomework(homework);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/homework/")
                        .header("Authorization", "Bearer "+ access_token_role_student)
                        .content(asJsonString(homework))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(403));
    }

    @Test
    @DisplayName("This tests api/homework/id put request for role teacher")
    void changeHomeworkRoleTeacherTest() throws Exception {

        doReturn(homework).when(service).changeHomeworkById(homework, "test_id");

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/homework/test_id")
                        .header("Authorization", "Bearer "+ access_token_role_teacher)
                        .content(asJsonString(homework))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(content().json("{}"));
    }

    @Test
    @DisplayName("This tests api/homework/id delete request for role teacher")
    void deleteHomeworkRolesTeacherTest() throws Exception {

        doReturn("Homework test_id deleted").when(service).deleteHomework("test_id");

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/homework/test_id")
                .header("Authorization", "Bearer "+ access_token_role_teacher))
                .andExpect(content().string("Homework test_id deleted"))
                .andExpect(status().is(200));
    }


    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
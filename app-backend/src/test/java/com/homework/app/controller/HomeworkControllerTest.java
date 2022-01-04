package com.homework.app.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.homework.app.model.Homework;
import com.homework.app.payload.HomeworkPayload;
import com.homework.app.payload.StatusPayload;
import com.homework.app.service.HomeworkServiceImpl;
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
import java.util.Optional;

import static com.homework.app.util.UtilJWT.CLAIM;
import static com.homework.app.util.UtilJWT.createToken;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static javax.servlet.http.HttpServletResponse.*;

@SpringBootTest
@AutoConfigureMockMvc
class HomeworkControllerTest {

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
    private Homework homework;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HomeworkServiceImpl service;

    @BeforeEach
    void setUp(){
        homework = new Homework();
        homework.setId("test_id");
        homework.setTitle("test_title");
        homework.setObjectives("test_objectives");
    }

    @Test
    @DisplayName("This tests api/homework get request with role student")
    void getAllHomeworkByStudentRoleTest() throws Exception {

        doReturn(Arrays.asList(homework)).when(service).getAllHomeworks();
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/homework/")
                    .header("Authorization", "Bearer "+ access_token_role_student))
                    .andExpect(status().is(SC_FORBIDDEN));
    }

    @Test
    @DisplayName("This tests api/homework get request with role teacher")
    void getAllHomeworkByTeacherRoleTest() throws Exception {

        doReturn(Arrays.asList(homework)).when(service).getAllHomeworks();
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/homework/")
                        .header("Authorization", "Bearer "+ access_token_role_teacher))
                .andExpect(status().is(SC_OK));
    }

    @Test
    @DisplayName("This tests api/homework/id get request with role teacher")
    void getHomeworkByIdByTeacherRoleTest() throws Exception {

        doReturn(Optional.of(homework)).when(service).getHomeworkById("test_id");
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/homework/test_id")
                .header("Authorization", "Bearer "+ access_token_role_teacher))
                .andExpect(content().json("{}"))
                .andExpect(status().is(SC_OK));
    }

    @Test
    @DisplayName("This tests api/homework/id get request with role student")
    void getHomeworkByIdByStudentRoleTest() throws Exception {

        doReturn(Optional.of(homework)).when(service).getHomeworkById("test_id");
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/homework/test_id")
                        .header("Authorization", "Bearer "+ access_token_role_student))
                .andExpect(status().is(SC_FORBIDDEN));
    }

    @Test
    @DisplayName("This tests api/homework/id get request with role student")
    void getHomeworkByStudentUsernameByTeacherRoleTest() throws Exception {

        doReturn(Arrays.asList(homework)).when(service).getHomeworksByStudentUsername("test_username");
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/homework/student/test_username")
                        .header("Authorization", "Bearer "+ access_token_role_teacher))
                .andExpect(status().is(SC_OK));
    }

    @Test
    @DisplayName("This tests api/homework/id get request with role student")
    void getHomeworkByStudentUsernameByStudentRoleTest() throws Exception {

        doReturn(Arrays.asList(homework)).when(service).getHomeworksByStudentUsername("test_username");
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/homework/student/test_username")
                        .header("Authorization", "Bearer "+ access_token_role_student))
                .andExpect(status().is(SC_FORBIDDEN));
    }

    @Test
    @DisplayName("This tests api/homework/student get request by role student")
    void getHomeworkOfCurrentlyLoggedInStudentByRoleStudent() throws Exception {

        doReturn(Arrays.asList(homework)).when(service).getHomeworksOfLoggedInStudent();
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/homework/student")
                        .header("Authorization", "Bearer "+ access_token_role_student))
                .andExpect(status().is(SC_OK));
    }

    @Test
    @DisplayName("This tests api/homework/student get request by role teacher")
    void getHomeworkOfCurrentlyLoggedInStudentByRoleTeacher() throws Exception {

        doReturn(Arrays.asList(homework)).when(service).getHomeworksOfLoggedInStudent();

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/homework/student")
                        .header("Authorization", "Bearer "+ access_token_role_teacher))
                .andExpect(status().is(SC_FORBIDDEN));
    }

    @Test
    @DisplayName("This tests api/homework/teacher get request by role teacher")
    void getHomeworkOfCurrentlyLoggedInTeacherByRoleTeacher() throws Exception {

        doReturn(Arrays.asList(homework)).when(service).getHomeworksOfLoggedInTeacher();
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/homework/teacher")
                        .header("Authorization", "Bearer "+ access_token_role_teacher))
                .andExpect(status().is(SC_OK));
    }

    @Test
    @DisplayName("This tests api/homework/id post request with role teacher")
    void addHomeworkByTeacherRoleTest() throws Exception {

        doReturn(homework).when(service).addHomework(any(HomeworkPayload.class));
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/homework/")
                        .header("Authorization", "Bearer "+ access_token_role_teacher)
                        .content(asJsonString(homework))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(SC_OK))
                .andExpect(content().json("{}"));
    }

    @Test
    @DisplayName("This tests api/homework/id post request with role student")
    void addHomeworkByStudentRoleTest() throws Exception {

        doReturn(homework).when(service).addHomework(any(HomeworkPayload.class));
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/homework/")
                        .header("Authorization", "Bearer "+ access_token_role_student)
                        .content(asJsonString(homework))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(SC_FORBIDDEN));
    }

    @Test
    @DisplayName("This tests api/homework/id put request with role teacher")
    void changeHomeworkByTeacherRoleTest() throws Exception {

        doReturn(homework).when(service).changeHomeworkById(any(HomeworkPayload.class), eq("test_id"));
        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/homework/test_id")
                        .header("Authorization", "Bearer "+ access_token_role_teacher)
                        .content(asJsonString(homework))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(SC_OK))
                .andExpect(content().json("{}"));
    }

    @Test
    @DisplayName("This tests api/homework/id put request with role student")
    void changeHomeworkByStudentRoleTest() throws Exception {

        doReturn(homework).when(service).changeHomeworkById(any(HomeworkPayload.class), eq("test_id"));
        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/homework/test_id")
                        .header("Authorization", "Bearer "+ access_token_role_student)
                        .content(asJsonString(homework))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(SC_FORBIDDEN));
    }

    @Test
    @DisplayName("This tests homework status change by student role")
    void changeHomeworkStatusByStudentToleTest() throws Exception {
        StatusPayload statusPayload = new StatusPayload();
        statusPayload.setStatus("test_status");

        doReturn(homework).when(service).changeHomeworkStatus(any(StatusPayload.class), eq("test_id"));
        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/homework/status/test_id")
                        .header("Authorization", "Bearer "+ access_token_role_student)
                        .content(asJsonString(statusPayload))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(SC_OK));
    }

    @Test
    @DisplayName("This tests api/homework/id delete request with role teacher")
    void deleteHomeworkByTeacherRolesTest() throws Exception {

        doReturn(homework).when(service).deleteHomework("test_id");
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/homework/test_id")
                .header("Authorization", "Bearer "+ access_token_role_teacher)
                .content(asJsonString(homework))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(SC_OK));
    }

    @Test
    @DisplayName("This tests api/homework/id delete request with role student")
    void deleteHomeworkByStudentRolesTest() throws Exception {

        doReturn(homework).when(service).deleteHomework("test_id");
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/homework/test_id")
                        .header("Authorization", "Bearer "+ access_token_role_student)
                .content(asJsonString(homework))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(SC_FORBIDDEN));
    }

    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
package com.homework.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.homework.model.Homework;
import com.homework.service.ServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(HomeworkController.class)
@ContextConfiguration(classes = HomeworkController.class)
class HomeworkControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceImpl service;

    private Homework homework;

    @BeforeEach
    void setUp(){
        homework = new Homework();
        homework.setId("test_id");
        homework.setTitle("test_title");
        homework.setObjectives("test_objectives");
    }

    @Test
    @DisplayName("This tests api/homework get request")
    void getAllHomeworkTest() throws Exception {

        doReturn(Arrays.asList(homework)).when(service).getAllHomeworks();

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/homework"))
                    .andExpect(content().json("[{}]"));
    }

    @Test
    @DisplayName("This tests api/homework/id get request")
    void getHomeworkByIdTest() throws Exception {

        doReturn(Optional.of(homework)).when(service).getHomeworkById("test_id");

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/homework/test_id"))
                .andExpect(content().json("{}"));
    }

    @Test
    @DisplayName("This tests api/homework/id post request")
    void addHomeworkTest() throws Exception {

        doReturn(homework).when(service).addHomework(homework);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/homework/")
                        .content(asJsonString(homework))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(content().json("{}"));
    }

    @Test
    @DisplayName("This tests api/homework/id put request")
    void changeHomeworkTest() throws Exception {

        doReturn(homework).when(service).changeHomeworkById(homework, "test_id");

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/homework/test_id")
                        .content(asJsonString(homework))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(content().json("{}"));


    }

    @Test
    @DisplayName("This tests api/homework/id delete request")
    void deleteHomeworkTest() throws Exception {

        doReturn("Homework test_id deleted").when(service).deleteHomework("test_id");

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/homework/test_id"))
                .andExpect(content().string("Homework test_id deleted"));
    }


    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
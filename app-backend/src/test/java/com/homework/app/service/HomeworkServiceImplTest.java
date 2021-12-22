package com.homework.app.service;


import com.homework.app.model.Homework;
import com.homework.app.respository.HomeworkRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import java.util.Arrays;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;


@DataMongoTest
class HomeworkServiceImplTest {

    private HomeworkServiceImpl service;
    private Homework homework;

    @Mock
    private HomeworkRepository homeworkRepositoryTest;

    @BeforeEach
    void setUp(){
        service = new HomeworkServiceImpl(homeworkRepositoryTest);

        homework = new Homework();
        homework.setId("test_id");
        homework.setTitle("test_title");
        homework.setObjectives("test_objectives");
    }

    @Test
    @DisplayName("This tests getHomeworkById service method")
    void getHomeworkByIdTest(){
        doReturn(Optional.of(homework)).when(homeworkRepositoryTest).findById("test_id");
        assertEquals(Optional.of(homework), service.getHomeworkById("test_id"));
    }

    @Test
    @DisplayName("This tests getAllHomework service method")
    void getAllHomeworkTest(){
        doReturn(Arrays.asList(homework)).when(homeworkRepositoryTest).findAll();
        assertEquals(Arrays.asList(homework), service.getAllHomeworks());
    }

    @Test
    @DisplayName("This tests addHomework service method")
    void addHomeworkServiceMethodTest(){
        doReturn(homework).when(homeworkRepositoryTest).save(any(Homework.class));
        assertEquals(homework, service.addHomework(homework));
    }

    @Test
    @DisplayName("This tests changeHomeworkById service method")
    void changeHomeworkByIdTest(){
        doReturn(Optional.of(homework)).when(homeworkRepositoryTest).findById("test_id");
        doReturn(homework).when(homeworkRepositoryTest).save(any(Homework.class));
        assertEquals(homework, service.changeHomeworkById(homework,"test_id"));

    }

    @Test
    @DisplayName("This deleteHomework service method")
    void deleteHomework(){
        assertEquals("Homework test_id deleted", service.deleteHomework("test_id"));
    }

}
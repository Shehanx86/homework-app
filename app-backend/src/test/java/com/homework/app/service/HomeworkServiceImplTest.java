package com.homework.app.service;


import com.homework.app.model.Homework;
import com.homework.app.payload.HomeworkPayload;
import com.homework.app.payload.StatusPayload;
import com.homework.app.respository.HomeworkRepository;
import com.homework.app.respository.MongoTemplateOperations;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;


@DataMongoTest
class HomeworkServiceImplTest {

    private HomeworkServiceImpl service;
    private Homework homework;
    private HomeworkPayload homeworkPayload;

    @Mock
    private HomeworkRepository homeworkRepositoryTest;

    @Mock
    private MongoTemplateOperations mongoTemplateOperations;

    @BeforeEach
    void setUp(){
        service = new HomeworkServiceImpl(homeworkRepositoryTest, mongoTemplateOperations);

        homework = new Homework();
        homework.setId("test_id");
        homework.setTitle("test_title");
        homework.setObjectives("test_objectives");
        homework.setAssignedTo("test_assignedTo");
        homework.setAssignedBy("test_assignedBy");
        homework.setCreatedAt(new Date());
        homework.setDeadline(new Date());
        homework.setLastUpdatedAt(new Date());
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
        homeworkPayload = new HomeworkPayload();
        homeworkPayload.setpId("test_id");
        homeworkPayload.setpTitle("test_title");
        homeworkPayload.setpObjectives("test_objectives");
        homeworkPayload.setpAssignedTo("test_assignedTo");
        homeworkPayload.setpAssignedBy("test_assignedBy");
        homeworkPayload.setpCreatedAt(new Date());
        homeworkPayload.setpDeadline(new Date());
        homeworkPayload.setpLastUpdatedAt(new Date());

        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        doReturn(authentication).when(securityContext).getAuthentication();
        doReturn("username").when(authentication).getName();
        SecurityContextHolder.setContext(securityContext);

        doReturn(homework).when(homeworkRepositoryTest).save(any(Homework.class));
        assertEquals(homework, service.addHomework(homeworkPayload));
    }

    @Test
    @DisplayName("This tests home status change when homework presents")
    void changeHomeworkStatusIfHomeworkPresentTest(){
        StatusPayload statusPayload = new StatusPayload();
        statusPayload.setStatus("test_status");
        doReturn(Optional.ofNullable(homework)).when(homeworkRepositoryTest).findById("test_id");
        doReturn(homework).when(homeworkRepositoryTest).save(homework);
        assertEquals(homework, service.changeHomeworkStatus(statusPayload,"test_id"));
    }

    @Test
    @DisplayName("This tests home status change when homework presents")
    void getHomeworksByStudentUsernameTest(){
        doReturn(Arrays.asList(homework)).when(mongoTemplateOperations).getHomeworksByStudentUsername("test_username");
        assertEquals(Arrays.asList(homework), service.getHomeworksByStudentUsername("test_username"));
    }

    @Test
    @DisplayName("This tests home status change when homework is not present")
    void changeHomeworkStatusIfHomeworkNotPresentTest(){
        StatusPayload statusPayload = new StatusPayload();
        statusPayload.setStatus("test_status");
        doReturn(Optional.ofNullable(null)).when(homeworkRepositoryTest).findById("test_id");
        assertEquals(null, service.changeHomeworkStatus(statusPayload,"test_id"));
    }


    @Test
    @DisplayName("This tests changeHomeworkById service method when homework is present")
    void changeHomeworkByIdIfHomeworkPresentTestTest(){
        homeworkPayload = new HomeworkPayload();
        homeworkPayload.setpId("test_id");
        homeworkPayload.setpTitle("test_title");
        homeworkPayload.setpObjectives("test_objectives");
        homeworkPayload.setpAssignedTo("test_assignedTo");
        homeworkPayload.setpAssignedBy("test_assignedBy");
        homeworkPayload.setpCreatedAt(new Date());
        homeworkPayload.setpDeadline(new Date());
        homeworkPayload.setpLastUpdatedAt(new Date());

        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        doReturn(authentication).when(securityContext).getAuthentication();
        doReturn("username").when(authentication).getName();
        SecurityContextHolder.setContext(securityContext);

        doReturn(homework).when(homeworkRepositoryTest).save(any(Homework.class));
        assertEquals(homework, service.addHomework(homeworkPayload));
        doReturn(Optional.ofNullable(homework)).when(homeworkRepositoryTest).findById("test_id");
        doReturn(homework).when(homeworkRepositoryTest).save(any(Homework.class));
        assertEquals(homework, service.changeHomeworkById(homeworkPayload,"test_id"));

    }

    @Test
    @DisplayName("This tests changeHomeworkById service method when homework is not present")
    void changeHomeworkByIdIfHomeworkNotPresentTestTest(){
        homeworkPayload = new HomeworkPayload();
        homeworkPayload.setpId("test_id");
        homeworkPayload.setpTitle("test_title");
        homeworkPayload.setpObjectives("test_objectives");
        homeworkPayload.setpAssignedTo("test_assignedTo");
        homeworkPayload.setpAssignedBy("test_assignedBy");
        homeworkPayload.setpCreatedAt(new Date());
        homeworkPayload.setpDeadline(new Date());
        homeworkPayload.setpLastUpdatedAt(new Date());

        doReturn(Optional.ofNullable(null)).when(homeworkRepositoryTest).findById("test_id");
        assertEquals(null, service.changeHomeworkById(homeworkPayload,"test_id"));

    }

    public List<Homework> getHomeworksOfLoggedInStudent(){
        return mongoTemplateOperations.getHomeworksOfLoggedInStudent();
    }

    @Test
    @DisplayName("This tests getting homeworks of currently logged in student")
    void getHomeworksOfLoggedInStudentTest(){
        doReturn(Arrays.asList(homework)).when(mongoTemplateOperations).getHomeworksOfLoggedInStudent();
        assertEquals(Arrays.asList(homework), service.getHomeworksOfLoggedInStudent());

    }

    @Test
    @DisplayName("This deleteHomework service method")
    void deleteHomework(){
        assertEquals("Homework test_id deleted", service.deleteHomework("test_id"));
    }

}
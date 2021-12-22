package com.homework.app.service;

import com.homework.app.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import static com.homework.app.util.UtilJWT.CLAIM;
import static com.homework.app.util.UtilJWT.createToken;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
@SpringBootTest
class RefreshTokenServiceTest {

    private String refresh_token = createToken(
            "user_test",
            new Date(System.currentTimeMillis() + 30 * 10 * 1000),
            "test_issuer",
            CLAIM,
            Arrays.asList("teacher")
    );
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private User user;
    private RefreshTokenService refreshTokenService;

    @Mock
    UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId("test_id");
        user.setName("test_name");
        user.setUsername("test_username");
        user.setPassword("test_password");
        user.setRole("teacher");

        refreshTokenService = new RefreshTokenService(userService);

        request = new MockHttpServletRequest("GET", "/api/token/refresh");
        response = new MockHttpServletResponse();
    }

    @Test
    @DisplayName("This tests creating new access token with valid refresh tokens")
    void createNewAccessTokenWithValidRefreshTokenTest() {
        request.addHeader("Authorization", "Bearer " + refresh_token);
        doReturn(user).when(userService).getUserByUsername(any(String.class));
        refreshTokenService.CreateNewAccessToken(request, response);
        assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("This tests creating new access token with invalid refresh tokens")
    void createNewAccessTokenWithInvalidAccessToken() {
        request.addHeader("Authorization", "invalid refresh token");
        doReturn(user).when(userService).getUserByUsername(any(String.class));
        refreshTokenService.CreateNewAccessToken(request, response);
        assertEquals(403, response.getStatus());
    }
}
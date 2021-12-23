package com.homework.app.controller;

import com.homework.app.service.AuthenticationService;
import com.homework.app.service.RefreshTokenService;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Date;

import static javax.servlet.http.HttpServletResponse.*;
import static com.homework.app.util.UtilJWT.CLAIM;
import static com.homework.app.util.UtilJWT.createToken;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    private MockMvc mockMvc;

    @MockBean
    RefreshTokenService refreshTokenService;

    @MockBean
    AuthenticationService authenticationService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private String refresh_token= createToken(
            "user_test",
            new Date(System.currentTimeMillis() + 30 * 10 * 1000),
            "test_issuer",
            CLAIM,
            Arrays.asList("teacher")
    );

    @BeforeEach()
    public void setup()
    {
        //Init MockMvc Object and build
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @DisplayName("This tests login post request")
    void loginTest() throws Exception {

        doReturn("login successful").when(authenticationService).login(any(HttpServletRequest.class), any(HttpServletResponse.class));

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("username", "test_name")
                .param("password", "test_password"))
                .andExpect(content().string("login successful"))
                .andExpect(status().is(SC_OK));
    }

    @Test
    @DisplayName("This tests refresh token get request")
    void refreshTokenTest() throws Exception {

        doReturn("New access token created").when(refreshTokenService).createNewAccessToken(any(HttpServletRequest.class), any(HttpServletResponse.class));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/token/refresh")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .header("Authorization", "Bearer "+ refresh_token))
                .andExpect(content().string("New access token created"))
                .andExpect(status().is(SC_OK));
    }

}
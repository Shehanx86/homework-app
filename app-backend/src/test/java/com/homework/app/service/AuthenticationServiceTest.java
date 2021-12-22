package com.homework.app.service;

import com.homework.app.filter.CustomAuthenticationFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

class AuthenticationServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private CustomAuthenticationFilter customAuthenticationFilter;

    private Authentication inputAuthentication;
    private Authentication resultAuthentication;
    private User userDetails;

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private com.homework.app.model.User user;

    @BeforeEach
    void setUp() {
        userDetails = new User("test_username", "test_password", Arrays.asList(new SimpleGrantedAuthority("teacher")));
        inputAuthentication = new UsernamePasswordAuthenticationToken("test_username", "test_password");
        resultAuthentication = new UsernamePasswordAuthenticationToken(userDetails, null, Arrays.asList(new SimpleGrantedAuthority("teacher")));

        user = new com.homework.app.model.User();
        user.setId("test_id");
        user.setName("test_name");
        user.setUsername("test_username");
        user.setPassword("test_password");
        user.setRole("teacher");

        request = new MockHttpServletRequest("POST", "/api/login");
        response = new MockHttpServletResponse();
        request.addParameter("username", "test_username");
        request.addParameter("password", "test_password");
    }

    @Test
    void login() {


        //CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter();

        doReturn(resultAuthentication).when(customAuthenticationFilter).attemptAuthentication(any(HttpServletRequest.class), any(HttpServletResponse.class));
        doReturn(resultAuthentication).when(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        doReturn(userDetails).when(resultAuthentication).getPrincipal();



        AuthenticationService authenticationService = new AuthenticationService(authenticationManager, customAuthenticationFilter);


        System.out.println(authenticationService.login(request, response));


      //  assertEquals(200, response.getStatus());



    }
}
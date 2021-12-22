package com.homework.app.filter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
class CustomAuthenticationFilterTest {

    @Mock
    private AuthenticationManager authenticationManager;

    private Authentication inputAuthentication;
    private Authentication resultAuthentication;
    private User userDetails;
    private CustomAuthenticationFilter customAuthenticationFilter;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;



    @BeforeEach
    void setUp(){
        userDetails = new User("test_username", "test_password", Arrays.asList(new SimpleGrantedAuthority("teacher")));
        inputAuthentication = new UsernamePasswordAuthenticationToken("test_username", "test_password");
        resultAuthentication = new UsernamePasswordAuthenticationToken(userDetails, null, Arrays.asList(new SimpleGrantedAuthority("teacher")));
        customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager);
        response = new MockHttpServletResponse();
        request = new MockHttpServletRequest("POST", "/api/login");
        request.addParameter("username", "test_username");
        request.addParameter("password", "test_password");

    }

    @Test
    @DisplayName("This tests attempAuthentication")
    void attemptAuthenticationTest() {
        doReturn(resultAuthentication).when(authenticationManager).authenticate(inputAuthentication);
        assertEquals(resultAuthentication, customAuthenticationFilter.attemptAuthentication(request, response));
    }
}
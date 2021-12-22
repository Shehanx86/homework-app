package com.homework.app.filter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
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

    @BeforeEach
    void setUp(){
        userDetails = new User("test_username", "test_password", Arrays.asList(new SimpleGrantedAuthority("teacher")));
        inputAuthentication = new UsernamePasswordAuthenticationToken("test_username", "test_password");
        resultAuthentication = new UsernamePasswordAuthenticationToken(userDetails, null, Arrays.asList(new SimpleGrantedAuthority("teacher")));
    }

    @Test
    void attemptAuthenticationTest() {

        doReturn(resultAuthentication).when(authenticationManager).authenticate(inputAuthentication);

        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager);

        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/api/login");
        request.addParameter("username", "test_username");
        request.addParameter("password", "test_password");

        MockHttpServletResponse response = new MockHttpServletResponse();

        assertEquals(resultAuthentication, customAuthenticationFilter.attemptAuthentication(request, response))
    ;

    }
}
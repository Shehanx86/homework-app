package com.homework.app.filter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import static com.homework.app.util.UtilJWT.CLAIM;
import static com.homework.app.util.UtilJWT.createToken;
import static org.junit.jupiter.api.Assertions.*;

class CustomAuthorizationFilterTest {

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private CustomAuthorizationFilter customAuthorizationFilter;
    private MockFilterChain filterChain;

    private String access_token = createToken(
            "user_test",
            new Date(System.currentTimeMillis() + 30 * 10 * 1000),
            "test_issuer",
            CLAIM,
            Arrays.asList("teacher")
    );

    @BeforeEach
    void setUp(){
        request = new MockHttpServletRequest("GET", "/api/users/teacher");;
        response = new MockHttpServletResponse();
        customAuthorizationFilter = new CustomAuthorizationFilter();
        filterChain = new MockFilterChain();
    }

    @Test
    @DisplayName("This tests authorization filter with invalid access token")
    void doFilterInternalWithInvalidTokenTest() throws ServletException, IOException {

        request.addHeader("Authorization", "invalid header");
        customAuthorizationFilter.doFilterInternal(request, response, filterChain);
        assertEquals(403, response.getStatus());
    }

    @Test
    @DisplayName("This tests authorization filter with valid access token")
    void doFilterInternalWithValidTokenTest() throws ServletException, IOException {

        request.addHeader("Authorization", "Bearer " + access_token);
        customAuthorizationFilter.doFilterInternal(request, response, filterChain);
        assertEquals(200, response.getStatus());
    }
}
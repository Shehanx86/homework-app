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

    private String access_token = createToken(
            "user_test",
            new Date(System.currentTimeMillis() + 30 * 10 * 1000),
            "test_issuer",
            CLAIM,
            Arrays.asList("teacher")
    );

    @Test
    @DisplayName("This tests authorization filter with invalid access token")
    void doFilterInternalInvalidTokenTest() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/users/teacher");
        request.addHeader("Authorization", "invalid header");

        MockHttpServletResponse response = new MockHttpServletResponse();

        CustomAuthorizationFilter customAuthorizationFilter = new CustomAuthorizationFilter();

        MockFilterChain filterChain = new MockFilterChain();

        customAuthorizationFilter.doFilterInternal(request, response, filterChain);
        assertEquals(403, response.getStatus());
    }

    @Test
    @DisplayName("This tests authorization filter with valid access token")
    void doFilterInternalValidTokenTest() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/users/teacher");
        request.addHeader("Authorization", "Bearer " + access_token);

        MockHttpServletResponse response = new MockHttpServletResponse();

        CustomAuthorizationFilter customAuthorizationFilter = new CustomAuthorizationFilter();

        MockFilterChain filterChain = new MockFilterChain();

        customAuthorizationFilter.doFilterInternal(request, response, filterChain);
        assertEquals(200, response.getStatus());
    }
}
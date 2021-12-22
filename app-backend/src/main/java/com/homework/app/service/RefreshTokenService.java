package com.homework.app.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.homework.app.model.User;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static javax.servlet.http.HttpServletResponse.*;
import static com.homework.app.util.TokenTester.validateToken;
import static com.homework.app.util.UtilJWT.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;


/**
 * creates new access token after expiration
 */
@Service
@NoArgsConstructor
public class RefreshTokenService {

    public RefreshTokenService(UserServiceImpl userService){
        this.userService = userService;
    }

    @Autowired
    UserServiceImpl userService;

    public String CreateNewAccessToken(HttpServletRequest request, HttpServletResponse response) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);

        /**
         * checks if received refresh token is valid
         */
            try{
                validateToken(authorizationHeader);

                DecodedJWT decodedJWT = decodeToken(authorizationHeader);

                String username = decodedJWT.getSubject();
                Date expiresAt = new Date(System.currentTimeMillis() + 10 * 60 * 1000);
                String issuer =  request.getRequestURL().toString();
                User user = userService.getUserByUsername(username);
                List<String> roles = Arrays.asList(user.getRole());

                /**
                 * creates new access token
                 */
                String new_access_token = createToken(
                        username,
                        expiresAt,
                        issuer,
                        CLAIM,
                        roles
                );

                response.setStatus(SC_OK);
                response.setHeader("access_token", new_access_token);
                return "New access token created";

            } catch (Exception error){
                response.setStatus(SC_FORBIDDEN);
                return "A new access token was not created "+error.getMessage();
            }
    }
}
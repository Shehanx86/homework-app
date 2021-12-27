package com.homework.app.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.homework.app.model.User;
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
public class RefreshTokenService {

    private UserServiceImpl userService;

    @Autowired
    public RefreshTokenService(UserServiceImpl userService){
        this.userService = userService;
    }

    public String createNewAccessToken(HttpServletRequest request, HttpServletResponse response) {
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
                String newAccessToken = createToken(
                        username,
                        expiresAt,
                        issuer,
                        CLAIM,
                        roles
                );

                response.setStatus(SC_OK);
                response.setHeader("access_token", newAccessToken);
                return "New access token created";

            } catch (Exception error){
                response.setStatus(SC_FORBIDDEN);
                return "A new access token was not created "+error.getMessage();
            }
    }
}

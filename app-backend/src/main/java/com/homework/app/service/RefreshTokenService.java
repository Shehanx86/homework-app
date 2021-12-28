package com.homework.app.service;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.homework.app.exception.InvalidTokenException;
import com.homework.app.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger logger = LoggerFactory.getLogger(RefreshTokenService.class);

    private UserServiceImpl userService;

    @Autowired
    public RefreshTokenService(UserServiceImpl userService){
        this.userService = userService;
    }

    public String createNewAccessToken(HttpServletRequest request, HttpServletResponse response) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);

        try {
            validateToken(authorizationHeader);

            DecodedJWT decodedJWT = decodeToken(authorizationHeader);
            String username = decodedJWT.getSubject();
            Date expiresAt = new Date(System.currentTimeMillis() + 10 * 60 * 1000);
            String issuer =  request.getRequestURL().toString();
            User user = userService.getUserByUsername(username);
            List<String> roles = Arrays.asList(user.getRole());

            String newAccessToken = createToken(
                    username,
                    expiresAt,
                    issuer,
                    CLAIM,
                    roles
            );
            logger.info("New access token created.");
            response.setStatus(SC_OK);
            response.setHeader("access_token", newAccessToken);
            return "New access token created";

        } catch (JWTCreationException error) {
            logger.error("Couldn't create new token. ",error);
            response.setStatus(SC_INTERNAL_SERVER_ERROR);
            return "A new access token was not created " + error.getMessage() ;

        } catch (JWTVerificationException error ) {
            logger.error("Couldn't decode refresh token. ", error);
            response.setStatus(SC_FORBIDDEN);
            return "Couldn't decode refresh token. " + error.getMessage();

        } catch (InvalidTokenException error){
            logger.error("Invalid refresh token. ",error);
            response.setStatus(SC_FORBIDDEN);
            return "Invalid refresh token." + error.getMessage();
        }
    }
}

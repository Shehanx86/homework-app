package com.homework.app.service;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.homework.app.filter.CustomAuthenticationFilter;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import static com.homework.app.util.UtilJWT.CLAIM;
import static com.homework.app.util.UtilJWT.createToken;
import static javax.servlet.http.HttpServletResponse.*;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    private CustomAuthenticationFilter customAuthenticationFilter;

    public AuthenticationService(){
        customAuthenticationFilter = new CustomAuthenticationFilter();
    }

    public String login(HttpServletRequest request, HttpServletResponse response){
        customAuthenticationFilter.setAuthenticationManager(authenticationManager);

        try{
            Authentication authentication = customAuthenticationFilter.attemptAuthentication(request, response);
            org.springframework.security.core.userdetails.User user = (User) authentication.getPrincipal();
            String username = user.getUsername();
            Date accessTokenExpiresAt = new Date(System.currentTimeMillis() + 10 * 60 * 1000);
            Date refreshTokenExpiresAt = new Date(System.currentTimeMillis() + 60 * 60 * 24 * 365 * 1000 );
            String issuer = request.getRequestURL().toString();
            List<String> claims =  user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

            String accessToken = createToken(
                    username,
                    accessTokenExpiresAt,
                    issuer,
                    CLAIM,
                    claims
            );
            String refreshToken = createToken(
                    username,
                    refreshTokenExpiresAt,
                    issuer,
                    CLAIM,
                    claims
            );

            logger.info("Login successful.");
            response.setStatus(SC_OK);
            response.setHeader("access_token", accessToken);
            response.setHeader("refresh_token", refreshToken);
            return "login successful";

        } catch (BadCredentialsException error){
            logger.error("Login unsuccessful. ",error);
            response.setStatus(SC_UNAUTHORIZED);
            return "login unsuccessful " + error.getMessage();

        } catch (JWTCreationException error){
            logger.error("Couldn't create token ",error);
            response.setStatus(SC_INTERNAL_SERVER_ERROR);
            return "Couldn't create token "+error.getMessage();
        }
    }
}

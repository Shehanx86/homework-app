package com.homework.app.service;

import com.homework.app.filter.CustomAuthenticationFilter;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
public class AuthenticationService {

    private CustomAuthenticationFilter customAuthenticationFilter;
    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthenticationService(AuthenticationManager authenticationManager, CustomAuthenticationFilter customAuthenticationFilter){
        this.authenticationManager = authenticationManager;
        this.customAuthenticationFilter = customAuthenticationFilter;
    }


    public String login(HttpServletRequest request, HttpServletResponse response){

        if(customAuthenticationFilter == null){
            customAuthenticationFilter = new CustomAuthenticationFilter();
        }

        customAuthenticationFilter.setAuthenticationManager(authenticationManager);

        try{
            Authentication authentication = customAuthenticationFilter.attemptAuthentication(request, response);
            org.springframework.security.core.userdetails.User user = (User) authentication.getPrincipal();
            String username = user.getUsername();
            Date accessTokenExpiresAt = new Date(System.currentTimeMillis() + 10 * 60 * 1000);
            Date refreshTokenExpiresAt = new Date(System.currentTimeMillis() + 60 * 60 * 24 * 365 * 1000 );
            String issuer = request.getRequestURL().toString();
            List<String> claims =  user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

            try {
                String access_token = createToken(
                        username,
                        accessTokenExpiresAt,
                        issuer,
                        CLAIM,
                        claims
                );
                String refresh_token =  createToken(
                        username,
                        refreshTokenExpiresAt,
                        issuer,
                        CLAIM,
                        claims
                );

                response.setStatus(SC_OK);
                response.setHeader("access_token", access_token);
                response.setHeader("refresh_token", refresh_token);
                return "login successful";

            } catch (Exception error){
                response.setStatus(SC_INTERNAL_SERVER_ERROR);
                return "login unsuccessful due to a server error!" + error.getMessage();
            }

        } catch (BadCredentialsException error){
            response.setStatus(SC_UNAUTHORIZED);
            return "login unsuccessful " + error.getMessage();
        }
    }
}

package com.homework.app.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import static com.homework.app.util.TokenTester.validateToken;
import static com.homework.app.util.UtilJWT.*;
import static com.homework.app.util.UtilResponseSetter.setErrorResponse;
import static java.util.Arrays.stream;
import static javax.servlet.http.HttpServletResponse.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

/**
 * Checks authorization token
 */
public class CustomAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if(request.getServletPath().equals("/api/login") || request.getServletPath().equals("/api/token/refresh") || request.getServletPath().startsWith("/api/test")) {
            filterChain.doFilter(request, response);
        } else {
            try {
                String authorizationHeader = request.getHeader(AUTHORIZATION);
                validateToken(authorizationHeader);
                DecodedJWT decodedJWT = decodeToken(authorizationHeader);
                String username = decodedJWT.getSubject();
                String[] roles = decodedJWT.getClaim(CLAIM).asArray(String.class);
                Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                stream(roles).forEach(role -> {
                    authorities.add(new SimpleGrantedAuthority(role));
                });
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                filterChain.doFilter(request, response);

            } catch (Exception error){
                setErrorResponse(response, SC_FORBIDDEN, "Token is not verified! " + error.getMessage());
            }

        }
    }
}

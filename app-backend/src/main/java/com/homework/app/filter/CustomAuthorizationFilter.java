package com.homework.app.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.homework.app.exception.InvalidTokenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger logger = LoggerFactory.getLogger(CustomAuthorizationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if(request.getServletPath().equals("/api/login") ||
                request.getServletPath().equals("/api/token/refresh") ||
                request.getServletPath().startsWith("/api/test") ||
                request.getServletPath().startsWith("/swagger-ui")) {
            filterChain.doFilter(request, response);
            logger.info("Filtered request without checking authorization.");

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
                logger.info("Filtered request after checking authorization.");

            } catch (InvalidTokenException error){
                setErrorResponse(response, SC_FORBIDDEN, "Token is not valid! " + error.getMessage());
                logger.error("Token is not valid. ", error);

            } catch (JWTVerificationException error){
                setErrorResponse(response, SC_FORBIDDEN, "Could not decode token " + error.getMessage());
                logger.error("Could not decode token. ", error);

            } catch (ServletException | IOException error){
                setErrorResponse(response, SC_INTERNAL_SERVER_ERROR, "Cannot filer request " + error.getMessage());
                logger.error("Cannot filer request", error);
            }
        }
    }
}

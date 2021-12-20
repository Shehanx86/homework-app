package com.homework.app.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.homework.app.model.Homework;
import com.homework.app.model.User;
import com.homework.app.service.ServiceImpl;
import com.homework.app.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.Role;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api")
public class HomeworkController {

    @Autowired
    ServiceImpl service;

    @Autowired
    UserServiceImpl userService;

    @PostMapping("/homework")
    public Homework addHomework(@RequestBody Homework homework){
        return service.addHomework(homework);
    }

    @GetMapping("/homework")
    public List<Homework> getAllHomework(){
        return service.getAllHomeworks();
    }

    @GetMapping("/homework/{id}")
    public Optional<Homework> getHomeworkById(@PathVariable String id){
        return service.getHomeworkById(id);
    }

    @PutMapping("/homework/{id}")
    public Homework changeHomeworkById(@RequestBody Homework homework, @PathVariable String id){
        return service.changeHomeworkById(homework, id);
    }

    @DeleteMapping("/homework/{id}")
    public String deleteHomework(@PathVariable String id){
        return service.deleteHomework(id);
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response){
            String authorizationHeader = request.getHeader(AUTHORIZATION);
            if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
                try {
                    String refresh_token = authorizationHeader.substring("Bearer ".length());
                    Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                    JWTVerifier verifier = JWT.require(algorithm).build();
                    DecodedJWT decodedJWT = verifier.verify(refresh_token);
                    String username = decodedJWT.getSubject();
                    User user = userService.getUserByUsername(username);
                    String access_token = JWT.create()
                            .withSubject(user.getUsername())
                            .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                            .withIssuer(request.getRequestURL().toString())
                            .withClaim("roles", user.getRoles())
                            .sign(algorithm);

                    response.setHeader("access_token", access_token);
                    response.setHeader("refresh_token", refresh_token);

                } catch (Exception e){
                    response.setHeader("error", e.getMessage());
                    response.setStatus(FORBIDDEN.value());
                }

            } else {
                response.setStatus(FORBIDDEN.value());
                throw new RuntimeException("Refresh token is missing!");


            }
    }


}

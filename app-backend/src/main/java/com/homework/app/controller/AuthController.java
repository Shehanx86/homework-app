package com.homework.app.controller;

import com.homework.app.service.AuthenticationService;
import com.homework.app.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("api/")
public class AuthController {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    RefreshTokenService refreshTokenService;

    @GetMapping("/token/refresh")
    public String refreshToken(HttpServletRequest request, HttpServletResponse response) {
        return refreshTokenService.createNewAccessToken(request, response);
    }

    @PostMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response){
        return authenticationService.login(request, response);
    }
}

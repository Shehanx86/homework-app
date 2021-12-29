package com.homework.app.controller;

import com.homework.app.service.AuthenticationService;
import com.homework.app.service.RefreshTokenService;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(value = "Refresh access tokem", notes = "Refresh expired access token using the refresh token")
    @GetMapping("/token/refresh")
    public String refreshToken(HttpServletRequest request, HttpServletResponse response) {
        return refreshTokenService.createNewAccessToken(request, response);
    }

    @PostMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response){
        return authenticationService.login(request, response);
    }
}

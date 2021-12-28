package com.homework.app.util;

import com.homework.app.exception.InvalidTokenException;

public class TokenTester {

    private TokenTester(){}

    public static void validateToken(String authorizationHeader) throws InvalidTokenException {
        if(authorizationHeader == null){
            throw new InvalidTokenException("Received authorization header is null!");
        } else if (!authorizationHeader.startsWith("Bearer ")){
            throw new InvalidTokenException("Authorization header must be prefixed with 'Bearer '.");
        }
    }
}
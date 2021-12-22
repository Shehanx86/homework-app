package com.homework.app.util;

public class TokenTester {
    public static void validateToken(String authorizationHeader) throws Exception {
        if(authorizationHeader == null){
            throw new Exception("Received authorization header is null!");
        } else if (!authorizationHeader.startsWith("Bearer ")){
            throw new Exception("Authorization header must be prefixed with 'Bearer '.");
        }
    }
}

package com.homework.app.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.List;

public class UtilJWT {

    private UtilJWT(){}

    public static final String SECRET = "secret";
    public static final String CLAIM = "roles";

    public static DecodedJWT decodeToken(String token){
        String refreshToken = token.substring("Bearer ".length());
        Algorithm algorithm = Algorithm.HMAC256(SECRET.getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(refreshToken);
    }

    public static String createToken(String username, Date expiresAt, String issuer, String claimName, List<String> claimList){
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(expiresAt)
                .withIssuer(issuer)
                .withClaim(claimName, claimList)
                .sign(Algorithm.HMAC256(SECRET.getBytes()));
    }

}

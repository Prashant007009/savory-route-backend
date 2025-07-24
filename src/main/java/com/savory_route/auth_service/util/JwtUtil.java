package com.savory_route.auth_service.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.savory_route.auth_service.usermodel.AuthModel;

import jakarta.annotation.PostConstruct;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    private JWTVerifier verifier;

    @PostConstruct
    public void init() {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        this.verifier = JWT.require(algorithm).build();
    }

    public String generateToken(AuthModel user) {
        return JWT.create()
                .withSubject(user.getId())
                .withClaim("fname", user.getFirstName())
                .withClaim("lname", user.getLastName())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 3600 * 1000)) // 1 hour
                .sign(Algorithm.HMAC256(secret));
    }

    public String validateAndGetUserId(String token) {
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getSubject();
    }
}


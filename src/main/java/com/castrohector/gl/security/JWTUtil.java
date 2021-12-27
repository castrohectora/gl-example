package com.castrohector.gl.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;


public class JWTUtil {

    public static String getJWTToken(String email) {

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + 600000))
                .signWith(SignatureAlgorithm.HS512, Constants.SECRET_KEY)
                .compact();
    }

}

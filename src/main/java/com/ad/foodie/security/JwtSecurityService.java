package com.ad.foodie.security;

import java.util.Date;
import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

@Service
public class JwtSecurityService {

    private static final long EXPIRATION_TIME = 15 * 60 * 1000; /// milliseconds ///-----15 minutes
    private static final String SECRET = "addsewauiyibbhfhhftyryeybvnbcnuygfrsrtihjoiuewwargdtrbjhgufterfccxgdfzxfcjhbhujuvbhgchgh";

    private static final SecretKey SIGNING_KEY = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    // generate token

    public String generateToken(String username) {

        String token = Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SIGNING_KEY, Jwts.SIG.HS256)
                .compact();

        return token;

    }

    /// get username from token

    public String getUsername(String token) {
        String username = Jwts.parser()
                .verifyWith(SIGNING_KEY).build()
                .parseSignedClaims(token).getPayload().getSubject();

        return username;

    }

    /// validate token
    
    public boolean validateToken( String token){

        Jwts.parser()
                .verifyWith(SIGNING_KEY).build()
                .parseSignedClaims(token);

        return  true;


    }

    public boolean isTokenExpired(String token){
        Date expiration=Jwts.parser()
                            .verifyWith(SIGNING_KEY).build()
                            .parseSignedClaims(token).getPayload().getExpiration();

        return expiration.before(new Date());
    }

}

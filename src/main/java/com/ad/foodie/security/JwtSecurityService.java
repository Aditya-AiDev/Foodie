package com.ad.foodie.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

@Service
public class JwtSecurityService {

    private static final long EXPIRATION_TIME = 15 * 60 * 1000; /// milliseconds ///-----15 minutes
    private static final long EXPIRATION_REFRESH_TIME = 24 * 60 * 60 * 1000; /// milliseconds ///-----24 hr
    private static final String SECRET = "addsewauiyibbhfhhftyryeybvnbcnuygfrsrtihjoiuewwargdtrbjhgufterfccxgdfzxfcjhbhujuvbhgchgh";

    private static final SecretKey SIGNING_KEY = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    private static final String ACCESS_TOKEN_TYPE="Bearer";
    private static final String REFRESH_TOKEN_TYPE="Refresh-Token";


    // generate token

    public String generateToken(String username, boolean isAccessToken ) {

        long exTime= isAccessToken ? EXPIRATION_TIME : EXPIRATION_REFRESH_TIME;

        ///Token Info
        String tokenType = isAccessToken ? ACCESS_TOKEN_TYPE : REFRESH_TOKEN_TYPE;
        //
        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put("typ", tokenType);
        //

        String token = Jwts.builder()
                .subject(username)
                .claims(claimsMap)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + exTime))
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

    ///finding token typ by token info---payload
    public boolean isAccessToken(String token){
        
        Claims claims = Jwts.parser()
                            .verifyWith(SIGNING_KEY).build()
                            .parseSignedClaims(token).getPayload();

        String tokenType = (String) claims.get("typ");

        return tokenType.equals(ACCESS_TOKEN_TYPE);
                            
                            
    }

    ///finding token typ by token info---payload
    public boolean isRefreshToken(String token){
        
        Claims claims = Jwts.parser()
                            .verifyWith(SIGNING_KEY).build()
                            .parseSignedClaims(token).getPayload();

        String tokenType = (String) claims.get("typ");

        return tokenType.equals(REFRESH_TOKEN_TYPE);
                            
                            
    }

}

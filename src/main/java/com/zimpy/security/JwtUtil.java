package com.zimpy.security;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.LocalDateTime;
import java.util.Date;

@Component
public class JwtUtil {
    private final String SECRETE_KEY="ABCDOILKADSKAHEWOIEKLSLKSKGSDJSDADPOPOHSDADA";
    private final long EXPIRATION_TIME = 1000*60*60*24;//24 hourse


    public String generateToken(Long userId, String role){
        return  Jwts.builder()
                .setSubject(userId.toString())
                .claim("role",role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256 ,SECRETE_KEY)
                .compact();
    }

    public Long extractUserId(String token){
        return Long.parseLong(Jwts.parser()
                .setSigningKey(SECRETE_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject()

        );
    }

    public String extractRole(String token){
        return Jwts.parser()
                .setSigningKey(SECRETE_KEY)
                .parseClaimsJws(token)
                .getBody()
                .get("role",String.class);
    }

    public boolean isTokenValid(String token){
        try{
            Jwts.parser().setSigningKey(SECRETE_KEY).parseClaimsJws(token);
            return true;
        }catch (Exception e){
            return false;
        }
    }



}

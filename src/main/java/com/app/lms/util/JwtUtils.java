package com.app.lms.util;

import com.app.lms.dto.AuthenticatedUser;
import com.fasterxml.jackson.core.type.TypeReference;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.provider}")
    private String jwtProvider;

    @Value("${jwt.token.validity.in.ms}")
    private Long jwtValidity;

    public String generateToken(AuthenticatedUser user){
        Map<String, Object> claims = Json.convertTo(user, new TypeReference<Map<String, Object>>() {});
        return Jwts.builder()
                .setSubject(user.getUserId().toString())
                .setClaims(claims)
                .setIssuer(this.jwtProvider)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + this.jwtValidity))
                .signWith(SignatureAlgorithm.HS256,this.jwtSecret)
                .compact();


    }

    public AuthenticatedUser validateAndGetAuthenticatedUser(String token){
        AuthenticatedUser user;
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(this.jwtSecret)
                    .parseClaimsJws(token)
                    .getBody();
            user = Json.convertTo(claims,AuthenticatedUser.class);
        }
        catch (Exception e){
            user=null;
        }
        return user;
    }
}

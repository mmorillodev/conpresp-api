package com.conpresp.conprespapi.service;

import com.conpresp.conprespapi.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class TokenService {

    @Value("${conpresp.jwt.expiration}")
    private String expiration;

    @Value("${conpresp.jwt.secret}")
    private String secret;

    public String generateToken(Authentication auth) {
        Date now = new Date();
        return Jwts.builder()
                .setIssuer("Conpresp API")
                .setSubject(((User) auth.getPrincipal()).getId())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + Long.parseLong(expiration)))
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    public boolean isValidToken(String token) {
        try {
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public String getUserId(String token) {
        Claims body = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
        return body.getSubject();
    }
}

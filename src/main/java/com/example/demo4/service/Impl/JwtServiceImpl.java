package com.example.demo4.service.Impl;
import com.example.demo4.contant.Role;
import com.example.demo4.service.JwtService;
import com.example.demo4.utils.JwtData;
import com.example.demo4.utils.StringProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Service
public class JwtServiceImpl implements JwtService {
    private static final String KEY = "a-very-secure-server-key-of-phoenix";

    public String generateToken(String email, Role role, String id) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        claims.put("role", role);
        claims.put("id", id);
        String token = Jwts.builder()
                .addClaims(claims)
                .signWith(SignatureAlgorithm.HS256, KEY.getBytes())
                .compact();
        return token;
    }

    @Override
    public String parseTokenToId(String token) {
        token = token.replace("Bearer ","");
        Claims claims = Jwts.parserBuilder().setSigningKey(StringProvider.KEY.getBytes()).build().
                parseClaimsJws(token).getBody();
        return claims.get(StringProvider.ID).toString();
    }

    @Override
    public Map<String, Object> parseTokenToClaims(String token) {
        token = token.replace("Bearer ","");
        Map<String, Object> claims = Jwts.parserBuilder().setSigningKey(StringProvider.KEY.getBytes()).build().
                parseClaimsJws(token).getBody();
        return claims;
    }


    public String testRole(String token) {
        token = token.replace("Bearer ","");
        Claims claims = Jwts.parserBuilder().setSigningKey(KEY.getBytes()).build()
                .parseClaimsJws(token).getBody();
        return claims.get("role").toString();
    }

    public String parseTokenToEmail(String token) {
        token = token.replace("Bearer ","");
        Claims claims = Jwts.parserBuilder().setSigningKey(KEY.getBytes()).build()
                .parseClaimsJws(token).getBody();
        return claims.get("role").toString();
    }

}

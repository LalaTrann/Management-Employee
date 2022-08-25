package com.example.demo4.service;

import com.example.demo4.contant.Role;
import org.springframework.stereotype.Service;


import java.util.Map;

public interface JwtService {

    String generateToken(String email, Role role, String id);
//    String parseTokenToRole(String token);
    String parseTokenToId(String token);
    Map<String, Object> parseTokenToClaims(String token);

    String testRole(String token);
    String parseTokenToEmail(String token);


}
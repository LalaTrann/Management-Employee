package com.example.demo4.utils;

import com.example.demo4.contant.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtData {
    private String email;
    private String id;
    private Role role;
}


package com.server.blogappserver.security;

import lombok.Data;

@Data
public class JwtAuthRequst {
    private String username;
    private String password;
}

package com.server.blogappserver.payloads;

import lombok.Data;

@Data
public class LogInDto {
    private String username;
    private String password;
}

package com.server.payloads;

import lombok.Data;

@Data
public class LogInDto {
    private String username;
    private String password;
}

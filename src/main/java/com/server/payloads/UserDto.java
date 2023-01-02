package com.server.payloads;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@Data
public class UserDto implements Serializable {
    private int id;
    private String name;
    private String email;
    private String password;
    private String about;
}

package com.server.services;

import com.server.entities.User;
import com.server.payloads.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto user);
    UserDto updateUser(UserDto user,Integer id);
    UserDto getUserById(Integer id);
    List<User> getAllUser();
    void deleteUser(Integer id);
    UserDto getUserByEmail(String email);
}

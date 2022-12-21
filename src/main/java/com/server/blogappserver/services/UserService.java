package com.server.blogappserver.services;

import com.server.blogappserver.payloads.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto user);
    UserDto updateUser(UserDto user,Integer id);
    UserDto getUserById(Integer id);
    List<UserDto> getAllUser();
    void deleteUser(Integer id);
}

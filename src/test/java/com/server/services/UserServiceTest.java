package com.server.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;


import com.server.entities.User;
import com.server.payloads.UserDto;
import com.server.repositories.UserRepo;
import com.server.services.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl business;

    @Mock
    private UserRepo repository;

    User user;
    UserDto userDto;

    @BeforeEach
    void init(){
        user=User.builder().id(1).build();
        userDto=UserDto.builder().id(1).build();
    }

    @Test
    public void getAllUsers() {
        when(repository.findAll()).thenReturn(Arrays.asList(user));
        List<User> items = business.getAllUser();
        assertEquals(1, items.get(0).getId());
    }

    @Test
    void updateUser() {
    }

    @Test
    void getUserById() {
    }

    @Test
    void deleteUser() {
    }

    @Test
    void getUserByEmail() {
    }
}
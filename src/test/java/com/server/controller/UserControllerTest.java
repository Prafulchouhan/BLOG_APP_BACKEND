package com.server.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.payloads.ApiResponce;
import com.server.payloads.UserDto;
import com.server.repositories.CustomConfigRepo;
import com.server.services.UserService;
import com.server.services.impl.CustomUserDetailService;
import com.server.util.JwtFilter;
import com.server.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.xml.transform.Result;
import java.nio.charset.Charset;
import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private CustomConfigRepo repo;
    @MockBean
    private CustomUserDetailService customUserDetailService;
    @MockBean
    private JwtUtil jwtUtil;
    @MockBean
    private JwtFilter jwtFilter;
    @Autowired
    private ObjectMapper objectMapper;

    private UserDto userDto;

    @BeforeEach
    public void init(){
        userDto=UserDto.builder().id(1)
                .email("praful@gmail.com")
                .about("this is about me")
                .name("praful chouhan")
                .password("xyz")
                .build();
    }

//    @Test
//    void getAllUser() throws Exception {
//        List<UserDto> userDtoList=new ArrayList<>(Arrays.asList(userDto,userDto));
//        when(userService.getAllUser()).thenReturn(userDtoList);
//        ResultActions result=mockMvc.perform(get("/api/users")
//                .contentType(MediaType.APPLICATION_JSON));
//        result.andDo(print())
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(jsonPath("$",hasSize(2)));
//    }

    @Test
    void getUserById() throws Exception {
        when(userService.getUserById(ArgumentMatchers.any())).thenReturn(userDto);
        ResultActions response=mockMvc.perform(get("/api/users/{id}",1l)
                .contentType(MediaType.APPLICATION_JSON));

        response.andDo(print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.name",is(userDto.getName())));
    }

    @Test
    void createUser() throws Exception {
        given(userService.createUser(ArgumentMatchers.any())).willAnswer((invocation->invocation.getArgument(0)));
        ResultActions response =mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)));
        response.andDo(print())
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void updateUser() throws Exception {

        when(userService.updateUser(ArgumentMatchers.any(),ArgumentMatchers.any())).thenReturn(userDto);

        ResultActions result=mockMvc.perform(put("/api/users/{id}",1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)));
        result.andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id",is(userDto.getId())));
    }

    @Test
    void deleteUser() throws Exception {
        ApiResponce apiResponce=new ApiResponce("deleted",true);
        userService.deleteUser(1);
        this.mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/users/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
//package com.server.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.server.repositories.CustomConfigRepo;
//import com.server.repositories.UserRepo;
//import com.server.services.UserService;
//import com.server.services.impl.CustomUserDetailService;
//import com.server.util.JwtAuthRequest;
//import com.server.util.JwtUtil;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//
//@WebMvcTest(controllers = AuthController.class)
//@AutoConfigureMockMvc(addFilters = false)
//@ExtendWith(MockitoExtension.class)
//class AuthControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private CustomConfigRepo repo;
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    UserRepo userRepo;
//
//    @MockBean
//    private CustomUserDetailService customUserDetailService;
//
//    private JwtAuthRequest jwtAuthRequest;
//
//    @MockBean
//    private JwtUtil jwtUtil;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @BeforeEach
//    public void init(){
//        jwtAuthRequest=new JwtAuthRequest("praful","xyz");
//    }
//
//    @Test
//    void generateToken() throws Exception {
//        when(jwtUtil.generateToken(any())).thenReturn("token");
//        ResultActions result=mockMvc.perform(get("/api/{tag}","tag")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(jwtAuthRequest)));
//        result.andDo(print())
//                .andExpect(MockMvcResultMatchers.status().isOk());
//    }
//}
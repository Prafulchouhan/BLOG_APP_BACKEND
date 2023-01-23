package com.server.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.payloads.ApiResponce;
import com.server.payloads.CategoryDto;
import com.server.payloads.UserDto;
import com.server.services.CategoryService;
import com.server.services.UserService;
import com.server.services.impl.CustomUserDetailService;
import com.server.util.JwtFilter;
import com.server.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.client.ResponseActions;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CategoryController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CustomUserDetailService customUserDetailService;
    @MockBean
    private JwtUtil jwtUtil;
    @MockBean
    private JwtFilter jwtFilter;
    @MockBean
    private CategoryService categoryService;

    private CategoryDto categoryDto;

    @BeforeEach
    public void init(){
        categoryDto=CategoryDto.builder().categoryId(1)
                .categoryDescription("description")
                .categoryTitle("new title")
                .build();
    }

    @Test
    void getAllCategory() throws Exception {
        List<CategoryDto> categoryList = new ArrayList<>(Arrays.asList(categoryDto,categoryDto));
        when(categoryService.getAllCategory()).thenReturn(categoryList );
        ResultActions result=mockMvc.perform(get("/api/category/")
                .contentType(MediaType.APPLICATION_JSON));
        result.andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$",hasSize(2)));

    }

    @Test
    void getCategoryById() throws Exception {
        when(categoryService.getCategoryById(any())).thenReturn(categoryDto);
        ResultActions result=mockMvc.perform(get("/api/category/{id}",1)
                .contentType(MediaType.APPLICATION_JSON));
        result.andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.categoryId",is(1)));
    }

    @Test
    void createCategory() throws Exception {
        when(categoryService.createCategory(any())).thenReturn(categoryDto);
        ResultActions result=mockMvc.perform(post("/api/category/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryDto)));
        result.andDo(print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.categoryId",is(1)));
    }

    @Test
    void updateCategory() throws Exception {
        when(categoryService.updateCategory(any(),any())).thenReturn(categoryDto);
        ResultActions result=mockMvc.perform(put("/api/category/{id}",1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryDto)));
        result.andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    void deleteCategoryById() throws Exception {
        categoryService.deleteCategory(1);
        this.mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/category/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
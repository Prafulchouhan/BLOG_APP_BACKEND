package com.server.controller;

import com.server.entities.Tags;
import com.server.payloads.PostDto;
import com.server.payloads.PostResponse;
import com.server.repositories.CustomConfigRepo;
import com.server.services.FileService;
import com.server.services.PostService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.payloads.ApiResponce;
import com.server.payloads.UserDto;
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

import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PostController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CustomConfigRepo repo;
    @MockBean
    private PostService postService;
    @MockBean
    private CustomUserDetailService customUserDetailService;
    @MockBean
    private JwtUtil jwtUtil;
    @MockBean
    private JwtFilter jwtFilter;
    @Autowired
    private ObjectMapper objectMapper;

    public PostDto postDto;

    @BeforeEach
    void init(){
        Set<Tags> tags=new HashSet<>();
               tags.add( Tags.builder().id(1).tagName("new name").build());
        postDto=postDto.builder().postId(1).addedDate(new Date())
                .tags(tags).title("new title").user(null)
                .category(null).imageName("image")
                .build();
    }

    @Test
    void createPost() throws Exception {
        when(postService.createPost(any(),any(),any())).thenReturn(postDto);
        ResultActions result=mockMvc.perform(post("/api/user/{userId}/category/{categoryId}/posts",1,1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postDto)));
        result.andDo(print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.postId",is(1)));
    }

    @Test
    void addTagToPost() throws Exception {
        when(postService.addTag(any(),any())).thenReturn(postDto);
        ResultActions result=mockMvc.perform(get("/api/post/{post_id}/tag/{tag_id}",1,1)
                .contentType(MediaType.APPLICATION_JSON));
        result.andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.tags[0].id",is(1)));
    }

    @Test
    void getPostsByUser() throws Exception {
        when(postService.getPostByUser(any())).thenReturn(new ArrayList<>(Arrays.asList(postDto,postDto)));
        ResultActions result=mockMvc.perform(get("/api/user/{id}/posts",1)
                .contentType(MediaType.APPLICATION_JSON));
        result.andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$",hasSize(2)));
    }

    @Test
    void getPostsByCategory() throws Exception {
        when(postService.getPostByCategory(any())).thenReturn(new ArrayList<>(Arrays.asList(postDto,postDto)));
        ResultActions result=mockMvc.perform(get("/api/category/{id}/posts",1)
                .contentType(MediaType.APPLICATION_JSON));
        result.andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$",hasSize(2)));
    }

    @Test
    void getAllPosts() throws Exception {
        PostResponse postResponse=PostResponse.builder().content(new ArrayList<>(Arrays.asList(postDto,postDto))).build();
        when(postService.getAllPost(any(),any(),any())).thenReturn(postResponse);
        ResultActions result=mockMvc.perform(get("/api/post")
                .contentType(MediaType.APPLICATION_JSON));
        result.andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.content[0].postId",is(1)));
    }

    @Test
    void getPostById() throws Exception {
        when(postService.getPostById(any())).thenReturn(postDto);
        ResultActions result=mockMvc.perform(get("/api/post/{id}",1)
                .contentType(MediaType.APPLICATION_JSON));
        result.andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.postId",is(1)));
    }

//    @Test
//    void searchByKeyword() throws Exception {
//        PostResponse postResponse=PostResponse.builder().content(new ArrayList<>(Arrays.asList(postDto,postDto))).build();
//        when(postService.searchPost(any(),any(),any(),any())).thenReturn(postResponse);
//        ResultActions result =mockMvc.perform(get("/api/post/search/{keyword}","key")
//                .contentType(MediaType.APPLICATION_JSON));
//        result.andDo(print())
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(jsonPath("$",hasSize(2)));
//    }

    @Test
    void deletePost() throws Exception {
        ApiResponce apiResponce=new ApiResponce("deleted",true);
        postService.deletePost(1);
        this.mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/post/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void updatePost() throws Exception {
        when(postService.updatePost(any(),any())).thenReturn(postDto);

        ResultActions result=mockMvc.perform(put("/api/post/{id}",1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postDto)));
        result.andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.postId",is(postDto.getPostId())));
    }
}
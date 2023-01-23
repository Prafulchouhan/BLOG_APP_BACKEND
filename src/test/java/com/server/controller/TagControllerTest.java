package com.server.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.entities.Post;
import com.server.entities.Tags;
import com.server.payloads.PostDto;
import com.server.payloads.UserDto;
import com.server.services.TagService;
import com.server.services.impl.CustomUserDetailService;
import com.server.util.JwtFilter;
import com.server.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = TagController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class TagControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TagService tagService;
    @MockBean
    private CustomUserDetailService customUserDetailService;
    @MockBean
    private JwtUtil jwtUtil;
    @MockBean
    private JwtFilter jwtFilter;
    @Autowired
    private ObjectMapper objectMapper;

    private Tags tags;
    private Post post;

    private PostDto postDto;

    @BeforeEach
    public void init(){
        Set<Post> postSet=new HashSet<>();
        postSet.add(post);
        tags=new Tags(1,"tag1", postSet);
    }

    @Test
    void getPostTags() throws Exception {
        List<PostDto> postList=new ArrayList<>(Arrays.asList(postDto,postDto));
        when(tagService.getPostByTags(any())).thenReturn( postList);
        ResultActions result=mockMvc.perform(get("/api/{tag}","tag")
                .contentType(MediaType.APPLICATION_JSON));
        result.andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void createTag() throws Exception {
        given(tagService.createTag(ArgumentMatchers.any())).willAnswer((invocation->invocation.getArgument(0)));
        ResultActions response =mockMvc.perform(post("/api/addTag")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tags)));
        response.andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getAllTags() throws Exception {
        List<Tags> tagsList=new ArrayList<>(Arrays.asList(tags,tags));
        when(tagService.getAllTags()).thenReturn(tagsList);
        ResultActions result=mockMvc.perform(get("/api/allTag")
                .contentType(MediaType.APPLICATION_JSON));
        result.andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$",hasSize(2)));
    }
}
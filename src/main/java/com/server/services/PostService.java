package com.server.services;

import com.server.payloads.PostDto;
import com.server.payloads.PostResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {

    //create
    PostDto createPost(PostDto postDto,Integer categoryId,Integer userId);
    //update
    PostDto updatePost(PostDto postDto,Integer postId);
    //delete
    void deletePost(Integer postId);
    //get all
    PostResponse getAllPost(Integer pagenNo, Integer pageSize, String sortBy);
    //get by Id
    PostDto getPostById(Integer postId);
    //get post by category
    List<PostDto> getPostByCategory(Integer categoryId);
    //get post by User
    List<PostDto> getPostByUser(Integer userId);
    //seacch post
    PostResponse searchPost(String keyword,Integer pageNo, Integer pageSize, String sortBy);

    PostDto addTag(Integer post_id,Integer tag_id);
}

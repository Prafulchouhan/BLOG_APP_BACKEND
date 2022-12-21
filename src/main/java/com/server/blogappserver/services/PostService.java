package com.server.blogappserver.services;

import com.server.blogappserver.entities.Post;
import com.server.blogappserver.payloads.CategoryDto;
import com.server.blogappserver.payloads.PostDto;
import com.server.blogappserver.payloads.PostResponse;
import org.springframework.stereotype.Service;

import java.util.List;


public interface PostService {

    //create
    PostDto createPost(PostDto postDto,Integer categoryId,Integer userId);
    //update
    PostDto updatePost(PostDto postDto,Integer postId);
    //delete
    void deletePost(Integer postId);
    //get all
    PostResponse getAllPost(Integer pagenNo, Integer pageSize,String sortBy);
    //get by Id
    PostDto getPostById(Integer postId);
    //get post by category
    List<PostDto> getPostByCategory(Integer categoryId);
    //get post by User
    List<PostDto> getPostByUser(Integer userId);
    //seacch post
    List<PostDto> searchPost(String keyword);
}

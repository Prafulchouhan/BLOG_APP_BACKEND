package com.server.services;

import com.server.payloads.PostDto;
import com.server.payloads.PostResponse;

import java.util.List;


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
    List<PostDto> searchPost(String keyword);

    PostDto addTag(Integer post_id,Integer tag_id);
}

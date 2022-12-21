package com.server.blogappserver.controller;

import com.server.blogappserver.entities.Post;
import com.server.blogappserver.payloads.ApiResponce;
import com.server.blogappserver.payloads.PostDto;
import com.server.blogappserver.payloads.PostResponse;
import com.server.blogappserver.services.FileService;
import com.server.blogappserver.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.AccessType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private FileService fileService;
    @Value("${project.image}")
    private String path;

    @PostMapping("user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(
            @RequestBody PostDto postDto,
            @PathVariable Integer userId,
            @PathVariable Integer categoryId
    ){
        return new ResponseEntity<>(this.postService
                .createPost(postDto,userId,categoryId), HttpStatus.CREATED);
    }

    @GetMapping("user/{id}/posts")
    public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer id){
        return new ResponseEntity<>(this.postService.getPostByUser(id),HttpStatus.OK);
    }

    @GetMapping("category/{id}/posts")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer id){
        return new ResponseEntity<>(this.postService.getPostByCategory(id),HttpStatus.OK);
    }

    @GetMapping("post")
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNo",defaultValue = "1",required = false) Integer pageNo,
            @RequestParam(value = "pageSize",defaultValue = "5",required = false) Integer pageSize,
            @RequestParam(value = "sortBy",defaultValue = "postId",required = false) String sortBy
    ){
        PostResponse postResponse=this.postService.getAllPost(pageNo,pageSize,sortBy);
        return new ResponseEntity<>(postResponse,HttpStatus.OK);
    }

    @GetMapping("post/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer id){
        return new ResponseEntity<>(this.postService.getPostById(id),HttpStatus.OK);
    }

    @GetMapping("post/search/{keyword}")
    public ResponseEntity<List<PostDto>> searchByKeyword(
            @PathVariable String keyword
    ){
        return new ResponseEntity<>(this.postService.searchPost(keyword),HttpStatus.OK);
    }
    @DeleteMapping("post/{id}")
    public ResponseEntity<ApiResponce> deletePost(@PathVariable Integer id){
        this.postService.deletePost(id);
        return new ResponseEntity<>(new ApiResponce("Post deleted seccesfully",true),HttpStatus.OK);
    }

    @PutMapping("post/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable Integer id
    , @RequestBody PostDto postDto){
        return new ResponseEntity<>(this.postService.updatePost(postDto,id),HttpStatus.OK);
    }

    @PostMapping("post/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadpostImage(
            @RequestParam("image")MultipartFile image,
            @PathVariable Integer postId
            ) throws IOException {

            PostDto postDto=this.postService.getPostById(postId);
            String fileName=this.fileService.uploadImage(path,image);
            postDto.setImageName(fileName);
            return this.updatePost(postId,postDto);
    }
}

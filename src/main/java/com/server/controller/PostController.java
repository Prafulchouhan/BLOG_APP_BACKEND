package com.server.controller;

import com.server.entities.Config;
import com.server.payloads.ApiResponce;
import com.server.payloads.PostDto;
import com.server.payloads.PostResponse;
import com.server.repositories.CustomConfigRepo;
import com.server.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/")
@CrossOrigin("*")
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private CustomConfigRepo configRepo;


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
    @GetMapping("post/{post_id}/tag/{tag_id}")
    public ResponseEntity<PostDto> addTagToPost(
            @PathVariable("post_id") Integer post_id,
            @PathVariable("tag_id") Integer tag_id
    ){
        PostDto post=this.postService.addTag(post_id,tag_id);
        return new ResponseEntity<>(post,HttpStatus.OK);
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
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) Integer pageNo,
            @RequestParam(value = "pageSize",defaultValue = "5",required = false) Integer pageSize,
            @RequestParam(value = "sortBy",defaultValue = "addedDate",required = false) String sortBy
    ){
        PostResponse postResponse=this.postService.getAllPost(pageNo,pageSize,sortBy);
        return new ResponseEntity<>(postResponse,HttpStatus.OK);
    }

    @PostMapping("/addConfig")
    public Config addConfig(
            @RequestBody Config config
    ){
        return this.configRepo.save(config);
    }

//    @GetMapping("config_post")
//    public ResponseEntity<PostResponse> getAllPostsUsingConfig(
//    ){
//
//        PostResponse postResponse=this.postService.getAllPost();
//        return new ResponseEntity<>(postResponse,HttpStatus.OK);
//    }


    @GetMapping("post/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer id){
        return new ResponseEntity<>(this.postService.getPostById(id),HttpStatus.OK);
    }

    @GetMapping("post/search/{keyword}")
    public ResponseEntity<PostResponse> searchByKeyword(
            @PathVariable String keyword,
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) Integer pageNo,
            @RequestParam(value = "pageSize",defaultValue = "5",required = false) Integer pageSize,
            @RequestParam(value = "sortBy",defaultValue = "addedDate",required = false) String sortBy
    ){
        RestTemplate restTemplate=new RestTemplate();
        PostResponse responseEntity= restTemplate.getForEntity("http://localhost:9191/api/post/search/"+keyword,PostResponse.class).getBody();
        return new ResponseEntity<>(responseEntity,HttpStatus.OK);
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

//    @PostMapping("post/image/upload/{postId}")
//    public ResponseEntity<PostDto> uploadpostImage(
//            @RequestParam("image")MultipartFile image,
//            @PathVariable Integer postId
//            ) throws IOException {
//
//            PostDto postDto=this.postService.getPostById(postId);
//            String fileName=this.fileService.uploadImage(path,image);
//            postDto.setImageName(fileName);
//            return this.updatePost(postId,postDto);
//    }
}

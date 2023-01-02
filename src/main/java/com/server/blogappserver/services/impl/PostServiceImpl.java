package com.server.blogappserver.services.impl;

import com.fasterxml.jackson.databind.util.ArrayIterator;
import com.server.blogappserver.entities.Category;
import com.server.blogappserver.entities.Post;
import com.server.blogappserver.entities.Tags;
import com.server.blogappserver.entities.User;
import com.server.blogappserver.exceptions.ResourceNotFoundException;
import com.server.blogappserver.payloads.PostDto;
import com.server.blogappserver.payloads.PostResponse;
import com.server.blogappserver.repositories.CategoryRepo;
import com.server.blogappserver.repositories.PostRepo;
import com.server.blogappserver.repositories.TagsRepo;
import com.server.blogappserver.repositories.UserRepo;
import com.server.blogappserver.services.PostService;
import org.modelmapper.ModelMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private final ModelMapper modelMapper;

    @Autowired
    private TagsRepo tagsRepo;

    public PostServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId) {
        User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","id",userId));
        Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","id",categoryId));
        Post post=this.modelMapper.map(postDto,Post.class);
        post.setImageName("default.png");
        post.setUser(user);
        post.setCategory(category);
        post.setAddedDate(new Date());
        post=this.postRepo.save(post);
        return this.modelMapper.map(post,PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","id",postId));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
        post=this.postRepo.save(post);
        return this.modelMapper.map(post,PostDto.class);
    }

    @Override
    public void deletePost(Integer postId) {
        Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","id",postId));
        this.postRepo.deleteByPostById(postId);
    }

    @Override
    public PostResponse getAllPost(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable p=PageRequest.of(pageNo,pageSize, Sort.by(sortBy).descending());
        Page<Post> pagePosts=this.postRepo.findAll(p);
        List<Post> posts=pagePosts.getContent();

        List<PostDto> postDtos=posts.stream().map(post -> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        PostResponse postResponse=new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePosts.getNumber());
        postResponse.setPageSize(pagePosts.getSize());
        postResponse.setTotalElements(pagePosts.getTotalElements());
        postResponse.setTotalPages(pagePosts.getTotalPages());
        postResponse.setLastPage(pagePosts.isLast());
        return postResponse;
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","id",postId));
        return this.postToPostDto(post);
    }

    @Override
    public List<PostDto> getPostByCategory(Integer categoryId) {
        Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("CAtegory","id",categoryId));
        List<Post> posts=this.postRepo.findByCategory(category);
        List<PostDto> postDtos=posts.stream()
                .map(post -> this.modelMapper.map(post,PostDto.class))
                .collect(Collectors.toList());
        return postDtos;
    }

    public void fun(){
        Pageable p=PageRequest.of(1,1, Sort.by("addedDate").descending());
        Page<Post> pagePosts=this.postRepo.findAll(p);
        List<Post> posts=pagePosts.getContent();
        System.out.println(posts.get(0).getId());
        this.deletePost(posts.get(0).getId());
    }

    @Override
    public List<PostDto> getPostByUser(Integer userId) {
        User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","id",userId));
        List<Post> posts=this.postRepo.findByUser(user);
        List<PostDto> postDtos=posts.stream().map(post -> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public List<PostDto> searchPost(String keyword) {
        List<Post> posts=this.postRepo.searchByKeyword("%"+keyword+"%");
        List<PostDto> postDtoLis=posts.stream().map(post -> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        return postDtoLis;
    }

    @Override
    public PostDto addTag(Integer post_id, Integer tag_id) {
        Post post=this.postRepo.findById(post_id).orElseThrow(()->new ResourceNotFoundException("Post","id",post_id));

        Tags tags=this.tagsRepo.findById(tag_id).orElseThrow(()->new ResourceNotFoundException("Tags","id",tag_id));
        post.addTag(tags);
        System.out.println("*******"+post.getId());
        System.out.println("*******"+tags.getId());
        System.out.println(post.getTags().getClass());
        return this.postToPostDto(post);
    }

    public Post postDtoToPost(PostDto postDto){
//        User user=User.builder().id(userDto.getId()).name(userDto.getName())
//                .email(userDto.getEmail()).password(userDto.getPassword())
//                .about(userDto.getAbout()).build();
        Post post=this.modelMapper.map(postDto,Post.class);
        return post;
    }
    public PostDto postToPostDto(Post post){
//                Post post1=Post.builder().postId(post.getPostId()).title(post.getTitle())
//                        .addedDate(post.getAddedDate()).content(post.getContent())
//                        .imageName(post.getImageName()).category(post.getCategory())
//                        .user(post.getUser()).build();
        PostDto postDto=this.modelMapper.map(post,PostDto.class);
        return postDto;
    }

}

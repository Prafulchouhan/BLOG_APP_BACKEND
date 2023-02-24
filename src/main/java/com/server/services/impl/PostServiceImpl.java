package com.server.services.impl;

import com.server.entities.*;
import com.server.exceptions.ResourceNotFoundException;
import com.server.payloads.PostDto;
import com.server.payloads.PostResponse;
import com.server.repositories.*;
import com.server.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private CustomConfigRepo configRepo;

    @Autowired
    private TagsRepo tagsRepo;

    public PostServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
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

        Map<String,String> configMap=new HashMap<>();
        List<Config> config=configRepo.findAll();
        System.out.println(config);
        config.stream().forEach(data->configMap.put(data.getName(),data.getValue()));
        Sort sort=configMap.get("post_sort_order").equals("ASC")? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable p=PageRequest.of(pageNo,
                Integer.parseInt(configMap.get("post_page_size")), sort);

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
    public PostResponse searchPost(String keyword,Integer pageNo, Integer pageSize, String sortBy) {
        Pageable p=PageRequest.of(pageNo,pageSize, Sort.by(sortBy).descending());
        Page<Post> pagePosts=this.postRepo.searchByKeyword("%"+keyword+"%",p);

        List<Post> posts=pagePosts.getContent();

        List<PostDto> postDtos=pagePosts.stream().map(post -> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
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

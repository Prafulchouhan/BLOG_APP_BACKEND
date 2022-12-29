package com.server.blogappserver.services.impl;

import com.server.blogappserver.entities.Post;
import com.server.blogappserver.entities.Tags;
import com.server.blogappserver.payloads.PostDto;
import com.server.blogappserver.repositories.PostRepo;
import com.server.blogappserver.repositories.TagsRepo;
import com.server.blogappserver.services.TagService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagsRepo tagsRepo;
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public List<PostDto> getPostByTags(String tagName) {
        Tags tags=this.tagsRepo.findByTagName(tagName);
        List<Post> post=this.tagsRepo.findPostByTagIdtags(tags.getId());
        List<PostDto> postDtos=post.stream()
                .map(posts -> this.modelMapper.map(posts,PostDto.class))
                .collect(Collectors.toList());
        return  postDtos;
    }

    @Override
    public Tags createTag(Tags tags){
        return this.tagsRepo.save(tags);
    }

    @Override
    public List<Tags> getAllTags() {
        return this.tagsRepo.findAll();
    }
}

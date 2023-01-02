package com.server.services.impl;

import com.server.entities.Post;
import com.server.entities.Tags;
import com.server.payloads.PostDto;
import com.server.repositories.PostRepo;
import com.server.repositories.TagsRepo;
import com.server.services.TagService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

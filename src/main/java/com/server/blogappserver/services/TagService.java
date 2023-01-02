package com.server.blogappserver.services;

import com.server.blogappserver.entities.Tags;
import com.server.blogappserver.payloads.PostDto;

import java.util.List;

public interface TagService {
    public List<PostDto> getPostByTags(String tags);
    public Tags createTag(Tags tags);

    public List<Tags> getAllTags();
}

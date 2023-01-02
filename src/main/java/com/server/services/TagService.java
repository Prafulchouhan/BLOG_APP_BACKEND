package com.server.services;

import com.server.entities.Tags;
import com.server.payloads.PostDto;

import java.util.List;

public interface TagService {
    public List<PostDto> getPostByTags(String tags);
    public Tags createTag(Tags tags);

    public List<Tags> getAllTags();
}

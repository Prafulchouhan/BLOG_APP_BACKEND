package com.server.blogappserver.controller;

import com.server.blogappserver.entities.Tags;
import com.server.blogappserver.payloads.PostDto;
import com.server.blogappserver.repositories.TagsRepo;
import com.server.blogappserver.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/tags/notnot")
    public List<PostDto> getPostTags(@PathVariable String tag){
        return this.tagService.getPostByTags(tag);
    }

    @PostMapping("/addTag")
    public Tags createTag(@RequestBody Tags tags){
        return this.tagService.createTag(tags);
    }

    @GetMapping("/allTag")
    public List<Tags> getAllTags(){
        return this.tagService.getAllTags();
    }

}

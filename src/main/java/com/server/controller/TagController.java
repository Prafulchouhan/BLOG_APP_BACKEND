package com.server.controller;

import com.server.entities.Tags;
import com.server.payloads.PostDto;
import com.server.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/{tag}")
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

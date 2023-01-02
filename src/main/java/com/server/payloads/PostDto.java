package com.server.payloads;

import com.server.entities.Tags;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private Integer postId;
    private String title;
    private String content;
    private String imageName;
    private Date addedDate;
    private CategoryDto category;
    private UserDto user;
    private Set<CommentDto> comments=new HashSet<>();
    private Set<Tags> tags=new HashSet<>();
}

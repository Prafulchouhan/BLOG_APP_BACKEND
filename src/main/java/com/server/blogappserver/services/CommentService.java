package com.server.blogappserver.services;

import com.server.blogappserver.payloads.CommentDto;
import com.server.blogappserver.payloads.PostDto;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto,Integer postId);
    void deleteComment(Integer commentId);
}

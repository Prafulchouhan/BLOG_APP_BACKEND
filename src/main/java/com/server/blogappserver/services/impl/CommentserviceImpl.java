package com.server.blogappserver.services.impl;

import com.server.blogappserver.entities.Comment;
import com.server.blogappserver.entities.Post;
import com.server.blogappserver.exceptions.ResourceNotFoundException;
import com.server.blogappserver.payloads.CommentDto;
import com.server.blogappserver.repositories.CommentRepo;
import com.server.blogappserver.repositories.PostRepo;
import com.server.blogappserver.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentserviceImpl implements CommentService {
    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {
        Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","id:",postId));
        Comment comment=this.modelMapper.map(commentDto,Comment.class);
        comment.setPost(post);
        comment=this.commentRepo.save(comment);
        return this.modelMapper.map(comment,CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment=this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","id",commentId));
        this.commentRepo.delete(comment);
    }
}

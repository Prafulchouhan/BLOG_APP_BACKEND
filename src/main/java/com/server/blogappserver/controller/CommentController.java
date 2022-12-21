package com.server.blogappserver.controller;

import com.server.blogappserver.payloads.ApiResponce;
import com.server.blogappserver.payloads.CommentDto;
import com.server.blogappserver.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class CommentController {
    @Autowired
    private CommentService commentService;


    @PostMapping("post/{postId}/comment")
    public ResponseEntity<CommentDto> createComment(
            @PathVariable Integer postId,
            @RequestBody CommentDto commentDto
    ){
        return new ResponseEntity<>(this.commentService.createComment(commentDto,postId), HttpStatus.CREATED);
    }

    @DeleteMapping("comment/{commentId}")
    public ResponseEntity<ApiResponce> deleteComment(
            @PathVariable Integer commentId
    ){
        this.commentService.deleteComment(commentId);
        return new ResponseEntity<>(new ApiResponce("Comment deleted successfully",true),HttpStatus.OK);
    }
}

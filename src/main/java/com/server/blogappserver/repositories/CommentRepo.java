package com.server.blogappserver.repositories;

import com.server.blogappserver.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment,Integer> {
}

package com.server.blogappserver.repositories;

import com.server.blogappserver.entities.Category;
import com.server.blogappserver.entities.Post;
import com.server.blogappserver.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepo extends JpaRepository<Post,Integer> {
    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);

    @Query("select p from post p where p.title like :key")
    List<Post> searchByKeyword(@Param("key") String keyword);
}

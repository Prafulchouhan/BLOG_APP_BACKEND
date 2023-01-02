package com.server.repositories;

import com.server.entities.Post;
import com.server.entities.Tags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TagsRepo extends JpaRepository<Tags,Integer> {
    Tags findByTagName(String tagName);

    @Query("SELECT tag.posts FROM tags tag INNER JOIN tag.posts post WHERE tag.id=:id")
    List<Post> findPostByTagIdtags(@Param("id") Integer id);
}

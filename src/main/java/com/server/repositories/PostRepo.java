package com.server.repositories;

import com.server.entities.Category;
import com.server.entities.Post;
import com.server.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface PostRepo extends JpaRepository<Post,Integer> {
    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);

    @Query("select p from post p where p.title like :key ")
    Page<Post> searchByKeyword(@Param("key") String keyword, Pageable pageable);

    @Query("delete from post p where p.id = :id")
    @Modifying
    @Transactional
    void deleteByPostById(@Param("id") Integer id);
}

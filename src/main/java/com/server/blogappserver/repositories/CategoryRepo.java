package com.server.blogappserver.repositories;

import com.server.blogappserver.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category,Integer> {
}

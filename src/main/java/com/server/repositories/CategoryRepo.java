package com.server.repositories;

import com.server.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepo extends JpaRepository<Category,Integer> {
    @Override
    List<Category> findAll();
}

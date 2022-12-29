package com.server.blogappserver.repositories;

import com.server.blogappserver.entities.Tags;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagsRepo extends JpaRepository<Tags,Integer> {
}

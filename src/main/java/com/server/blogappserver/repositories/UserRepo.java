package com.server.blogappserver.repositories;

import com.server.blogappserver.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Integer> {

}

package com.server.repositories;

import com.server.entities.Config;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Configuration
public interface CustomConfigRepo extends JpaRepository<Config,String> {
//    Optional<CustomConfig> findByKey(String key);
}

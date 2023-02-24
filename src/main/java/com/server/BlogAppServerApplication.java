package com.server;

import com.server.entities.Config;
import com.server.repositories.CustomConfigRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootApplication
@EnableCaching
@EnableScheduling
public class BlogAppServerApplication implements CommandLineRunner {
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private CustomConfigRepo repo;
	@Autowired
	private CustomConfigRepo configRepo;

	public static void main(String[] args) {

		SpringApplication.run(BlogAppServerApplication.class, args);

	}
	@Override
	public void run(String... args) throws Exception {
//		Config config=new Config("sort_type","ase");
//		repo.save(config);
//		System.out.println(this.passwordEncoder.encode("xyz"));
	}
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}
}

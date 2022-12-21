package com.server.blogappserver;

import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BlogAppServerApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BlogAppServerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		System.out.println("*******");
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}
}

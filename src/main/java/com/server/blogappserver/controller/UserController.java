package com.server.blogappserver.controller;

import com.server.blogappserver.exceptions.ResourceNotFoundException;
import com.server.blogappserver.payloads.ApiResponce;
import com.server.blogappserver.payloads.LogInDto;
import com.server.blogappserver.payloads.UserDto;
import com.server.blogappserver.services.UserService;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@CacheConfig(cacheNames = "User")
@CrossOrigin("*")
public class UserController {
    Logger logger= LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUser(){
        List<UserDto> userDtoList=this.userService.getAllUser();
        logger.info("got all the users");
        return new ResponseEntity<>(userDtoList,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Cacheable(key = "#id",value = "User")
    public UserDto getUserById(@PathVariable Integer id){
        UserDto userDto=this.userService.getUserById(id);
        if(userDto!=null){
            logger.info("login the user by id {}",id);
        }else {
            logger.info("there is no user found for User id {}",id);
        }
        System.out.println(new ResponseEntity<>(userDto,HttpStatus.OK).getBody().getId());
//        return new ResponseEntity<>(userDto,HttpStatus.OK);
        return userDto;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> createUser(@RequestBody  UserDto userDto){

        UserDto user=this.userService.createUser(userDto);
        if(userDto!=null){
            logger.info("New user signUp");
        }else {
            logger.info("Something went wrong");
        }
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LogInDto> LogInUser(@RequestBody LogInDto logInDto) throws Exception {
        UserDto userDto=this.userService.getUserByEmail(logInDto.getUsername());
        if(!userDto.getPassword().equals(logInDto.getPassword())) {
            throw new ResourceNotFoundException("Pssword",logInDto.getPassword(),null);
        }
        return new ResponseEntity<>(logInDto,HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Integer id,@RequestBody  UserDto userDto){
        UserDto user=this.userService.updateUser(userDto,id);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponce> deleteUser(@PathVariable Integer id){
        this.userService.deleteUser(id);
        return new ResponseEntity<>(new ApiResponce("User deleted",true),HttpStatus.OK);
    }
}

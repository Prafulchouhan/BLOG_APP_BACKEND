package com.server.controller;

import com.server.entities.User;
import com.server.payloads.ApiResponce;
import com.server.payloads.UserDto;
import com.server.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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


    @GetMapping("/{id}")
    @Cacheable(key = "#id",value = "User")
    @ResponseStatus(HttpStatus.OK)
    public UserDto getUserById(@PathVariable Integer id){
        UserDto userDto=this.userService.getUserById(id);
        if(userDto!=null){
            logger.info("login the user by id {}",id);
        }else {
            logger.info("there is no user found for User id {}", id);
        }
        System.out.println(new ResponseEntity<>(userDto,HttpStatus.OK).getBody().getId());
//        return new ResponseEntity<>(userDto,HttpStatus.OK);
        return userDto;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@RequestBody  UserDto userDto){
        UserDto user=this.userService.createUser(userDto);
        if(userDto!=null){
            logger.info("New user signUp");
        }else {
            logger.info("Something went wrong");
        }
        return user;
    }

    @PutMapping("/{id}")
    @CachePut(key = "#id",value = "User")
    @ResponseStatus(HttpStatus.OK)
    public UserDto updateUser(@PathVariable Integer id,@RequestBody  UserDto userDto){
        UserDto user=this.userService.updateUser(userDto,id);
        return user;
    }

    @DeleteMapping("/{id}")
    @CacheEvict(key = "#id",value = "User")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponce deleteUser(@PathVariable Integer id){
        this.userService.deleteUser(id);
        return new ApiResponce("User deleted",true);
    }
}

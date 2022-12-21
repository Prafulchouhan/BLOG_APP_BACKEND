package com.server.blogappserver.controller;

import com.server.blogappserver.payloads.ApiResponce;
import com.server.blogappserver.payloads.UserDto;
import com.server.blogappserver.services.UserService;
import lombok.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUser(){
        List<UserDto> userDtoList=this.userService.getAllUser();
        return new ResponseEntity<>(userDtoList,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Integer id){
        UserDto userDto=this.userService.getUserById(id);
        return new ResponseEntity<>(userDto,HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@RequestBody  UserDto userDto){
        UserDto user=this.userService.createUser(userDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
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

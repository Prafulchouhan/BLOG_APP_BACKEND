package com.server.services.impl;

import com.server.entities.User;
import com.server.exceptions.ResourceNotFoundException;
import com.server.payloads.UserDto;
import com.server.repositories.UserRepo;
import com.server.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
        User user=UserDtoToUser(userDto);
        user=this.userRepo.save(user);
        return this.UserToUserDto(user);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer id) {
        User user=this.userRepo.findById(id)
                .orElseThrow((()->new ResourceNotFoundException("User"," id ",id)));
        user.setAbout(userDto.getAbout());
        user.setName(userDto.getName());
        user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        user=this.userRepo.save(user);
        return this.UserToUserDto(user);
    }

    @Override
    public UserDto getUserById(Integer id) {
        User user=this.userRepo.findById(id)
                .orElseThrow((()->new ResourceNotFoundException("User"," id ",id)));
        return this.UserToUserDto(user);
    }

    @Override
    public List<User> getAllUser() {
        List<User> userList=this.userRepo.findAll();
//        List<UserDto> userDtoList=userList.stream().map(user -> this.UserToUserDto(user)).collect(Collectors.toList());
        return userList;
    }

    @Override
    public void deleteUser(Integer id) {
        User user=this.userRepo.findById(id)
                .orElseThrow((()->new ResourceNotFoundException("User"," id ",id)));
        this.userRepo.delete(user);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user=this.userRepo.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("User","enamil     "+email,0));
        return this.UserToUserDto(user);
    }

    public User UserDtoToUser(UserDto userDto){
//        User user=User.builder().id(userDto.getId()).name(userDto.getName())
//                .email(userDto.getEmail()).password(userDto.getPassword())
//                .about(userDto.getAbout()).build();
        User user=this.modelMapper.map(userDto,User.class);
        return user;
    }
    public UserDto UserToUserDto(User user){
        UserDto userDto=this.modelMapper.map(user,UserDto.class);
        return userDto;
    }
}

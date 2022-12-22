package com.server.blogappserver.security;

import com.server.blogappserver.entities.User;
import com.server.blogappserver.exceptions.ResourceNotFoundException;
import com.server.blogappserver.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=this.userRepo.findByEmail(username)
                .orElseThrow(()->new ResourceNotFoundException("User","username: "+username,0));
        System.out.println(user);
        return user;
    }
}

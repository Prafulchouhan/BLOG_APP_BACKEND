package com.server.blogappserver.controller;

import com.server.blogappserver.repositories.UserRepo;
import com.server.blogappserver.util.JwtAuthRequest;
import com.server.blogappserver.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepo userRepo;

    @PostMapping("/login")
    public String generateToken(@RequestBody JwtAuthRequest jwtAuthRequest) throws Exception {
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            jwtAuthRequest.getUserName(),jwtAuthRequest.getPassword()
                    ));
        }catch (Exception e){
            throw new Exception("Username or password is wrong");
        }
        return jwtUtil.generateToken(jwtAuthRequest.getUserName());
    }
}

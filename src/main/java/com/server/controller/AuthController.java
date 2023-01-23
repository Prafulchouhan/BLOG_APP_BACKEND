package com.server.controller;

import com.server.services.UserService;
import com.server.util.JwtAuthRequest;
import com.server.util.JwtAuthResponse;
import com.server.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> generateToken(@RequestBody JwtAuthRequest jwtAuthRequest) throws Exception {
        System.out.println(jwtAuthRequest.getUsername()+"*****"+jwtAuthRequest.getPassword());
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            jwtAuthRequest.getUsername(),jwtAuthRequest.getPassword()
                    ));
        }catch (Exception e){
            throw new Exception("Username or password is wrong");
        }
        JwtAuthResponse jwtAuthResponse=new JwtAuthResponse();
        jwtAuthResponse.setToken(jwtUtil.generateToken(jwtAuthRequest.getUsername()));
        jwtAuthResponse.setUser(userService.getUserByEmail(jwtAuthRequest.getUsername()));
        return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
    }
}

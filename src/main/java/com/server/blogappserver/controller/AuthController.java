package com.server.blogappserver.controller;

import com.server.blogappserver.security.JwtAuthRequst;
import com.server.blogappserver.security.JwtAuthResponce;
import com.server.blogappserver.security.JwtTokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {
    @Autowired
    private JwtTokenHelper jwtTokenHelper;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponce> createToken(
            @RequestBody JwtAuthRequst jwtAuthRequst
            ) throws Exception {
            this.authenticate(jwtAuthRequst.getUsername(),jwtAuthRequst.getPassword());
            UserDetails userDetails=this.userDetailsService.loadUserByUsername(jwtAuthRequst.getUsername());
            String token =  this.jwtTokenHelper.generateToken(userDetails);
            JwtAuthResponce responce =new JwtAuthResponce();
            responce.setToken(token);
            return new ResponseEntity<>(responce, HttpStatus.OK);
    }

    private void authenticate(String username, String password) throws Exception {
        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(username,password);
        try{
            this.authenticationManager.authenticate(authenticationToken);
        }catch (BadCredentialsException e){
            System.out.println("Invalid credentail !!");
            throw new Exception("Invalid username or password !!");
        }
    }
}

package com.server.blogappserver.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestToken= request.getHeader("Authorization");
        System.out.println(request);
        String username=null;
        String token=null;
        if(request!=null && requestToken.startsWith("Bearer")){
            token=requestToken.substring(7);
            try {
                username=this.jwtTokenHelper.getUserNameFromToken(token);
            }
            catch (IllegalArgumentException e){
                System.out.println("unable to get jwt token");
            }
            catch (ExpiredJwtException e){
                System.out.println("Jwt token has expired");
            }
            catch (MalformedJwtException e){
                System.out.println("Invalid Jwt token");
            }
        }else{
            System.out.println("jwt token dose not begin with bearer");
        }

        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails=this.userDetailsService.loadUserByUsername(username);
            if(this.jwtTokenHelper.validateToken(token,userDetails)){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }else {
                System.out.println("Invalid jwt token");
            }
        }else{
            System.out.println("Username is null or contest is not null !!");
        }

        filterChain.doFilter(request,response);
    }
}

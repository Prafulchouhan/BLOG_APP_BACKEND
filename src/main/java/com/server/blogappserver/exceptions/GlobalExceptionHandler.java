package com.server.blogappserver.exceptions;

import com.server.blogappserver.payloads.ApiResponce;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ApiResponce> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
        String message=ex.getMessage();
        ApiResponce apiResponce=new ApiResponce(message,false);
        return new ResponseEntity<>(apiResponce, HttpStatus.NOT_FOUND);
    }
}

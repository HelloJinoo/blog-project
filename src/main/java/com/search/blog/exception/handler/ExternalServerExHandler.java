package com.search.blog.exception.handler;

import com.search.blog.common.ApiResponse;
import com.search.blog.exception.ExternalServerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExternalServerExHandler {

    @ExceptionHandler(value = {ExternalServerException.class})
    public ResponseEntity<?> serverExceptionHandle(ExternalServerException e){
        return new ResponseEntity(ApiResponse.error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

package com.search.blog.exception.handler;

import com.search.blog.common.ApiResponse;
import com.search.blog.exception.ExternalRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExternalRequestExHandler {

    @ExceptionHandler(value = {ExternalRequestException.class})
    public ResponseEntity<?> requestExceptionHandle(ExternalRequestException e){
        return new ResponseEntity(ApiResponse.fail(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}

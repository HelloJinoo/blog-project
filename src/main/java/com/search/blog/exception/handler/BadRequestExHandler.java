package com.search.blog.exception.handler;

import com.search.blog.common.ApiResponse;
import com.search.blog.exception.ExternalRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BadRequestExHandler {

    @ExceptionHandler(value = {ServletRequestBindingException.class})
    public ResponseEntity<?> badRequestExceptionHandle(){
        return new ResponseEntity(ApiResponse.fail("잘못된 요청입니다."), HttpStatus.BAD_REQUEST);
    }
}

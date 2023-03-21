package com.search.blog.exception.handler;

import com.search.blog.common.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class InterServerExHandler {


    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> interServerExceptionHandle(Exception e){
        log.info("Error Occur!! " , e);
        return new ResponseEntity<>(ApiResponse.error("Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

package com.search.blog.common;

import lombok.Getter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class ApiResponse<T> {

    private String status;
    private T data;
    private String message;

    public static <T> ApiResponse<T> success(T data){
        return new ApiResponse<>("success", data, null);
    }

    public static <T> ApiResponse<T> fail(BindingResult bindingResult){
            Map<String, String> errors = new HashMap<>();

            List<ObjectError> allErrors = bindingResult.getAllErrors();
            for (ObjectError error : allErrors) {
                if (error instanceof FieldError) {
                    errors.put(((FieldError) error).getField(), error.getDefaultMessage());
                } else {
                    errors.put( error.getObjectName(), error.getDefaultMessage());
                }
            }
            return new ApiResponse("fail", errors, null);
    }

    public static <T> ApiResponse<T> fail(T data){
        return new ApiResponse("fail", data, null);
    }

    public static <T> ApiResponse<T> error(String message){
        return new ApiResponse<>("error" , null, message);
    }

    public ApiResponse(String status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }
}

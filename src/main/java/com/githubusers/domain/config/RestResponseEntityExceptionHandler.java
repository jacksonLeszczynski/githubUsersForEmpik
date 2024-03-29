package com.githubusers.domain.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.FileNotFoundException;
import java.io.IOException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    
    private static final String NOT_AUTHORIZE_MSG = "URL was not authorized: please refresh token";

    @ExceptionHandler(value = {IOException.class, FileNotFoundException.class})
    protected ResponseEntity<Object> handleConflict(RuntimeException ex) {
        String msg = isNotAuthorizedUrl(ex) ? NOT_AUTHORIZE_MSG : "User Not Found";

        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, msg, ex));
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
    
    private boolean isNotAuthorizedUrl(RuntimeException ex) {
        return ex.getCause().getMessage().contains("response code: 401");
    }
}

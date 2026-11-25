package com.imaginarynil.broadcast_server.handler;

import com.imaginarynil.broadcast_server.user.UserExistsException;
import com.imaginarynil.broadcast_server.user.UserNotFoundException;
import jakarta.annotation.Nullable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private ResponseEntity<Object> buildResponseEntity(ApiError err) {
        return new ResponseEntity<>(err, err.getStatus());
    }

    @Override
    protected @Nullable ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String error = "Malformed JSON request";
        return this.buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, e));
    }

    @ExceptionHandler(UserExistsException.class)
    protected ResponseEntity<Object> handleUserExistsException(UserExistsException e) {
        ApiError err = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        return this.buildResponseEntity(err);
    }

    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException e) {
        ApiError err = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        return this.buildResponseEntity(err);
    }
}

package com.example.mvc2.exhandler.advice;

import com.example.mvc2.exception.UserException;
import com.example.mvc2.exhandler.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExControllerAdvice {

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> handleIllegalStateException(final RuntimeException e) {
        log.error("IllegalStateException", e);
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage(), "BAD", HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler
    public ResponseEntity<?> handleUserException(final UserException e) {
        log.error("UserException", e);
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage(), "USER-EX", HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> internalServerError(final Exception e) {
        log.error("InternalServerError", e);
        final ErrorResponse response = new ErrorResponse("서버 오류", "EX", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

}

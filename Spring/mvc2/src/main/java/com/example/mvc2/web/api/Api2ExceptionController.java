package com.example.mvc2.web.api;

import com.example.mvc2.exception.BadRequestException;
import com.example.mvc2.exception.UserException;
import com.example.mvc2.exhandler.ErrorResponse;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
public class Api2ExceptionController {
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

    @GetMapping("/api2/members/{id}")
    public ResponseEntity<?> getMember(@PathVariable final String id) {
        if (id.equals("ex")) {
            throw new IllegalStateException();
        }

        if (id.equals("bad")) {
            throw new IllegalArgumentException("잘못된 입력 값");
        }

        if (id.equals("user-ex")) {
            throw new UserException("사용자 오류");
        }

        return ResponseEntity.ok(new MemberDto(id, "hello " + id));
    }

    @GetMapping("/api2/bad")
    public ResponseEntity<?> getBad() {
        throw new BadRequestException();
    }

    @GetMapping("/api2/custom-builtin-ex")
    public ResponseEntity<?> getCustomBuiltinEx() {
        throw new ResponseStatusException(HttpStatus.CONFLICT, "잘못된 입력 값", new IllegalArgumentException());
    }

    @GetMapping("/api2/default-handler-ex")
    public ResponseEntity<?> getDefaultHandlerEx(@RequestParam final Integer data) {
        return ResponseEntity.ok(data);
    }

    @Getter
    static class MemberDto {
        private final String memberId;
        private final String name;

        public MemberDto(final String memberId, final String name) {
            this.memberId = memberId;
            this.name = name;
        }
    }
}

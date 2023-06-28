package com.example.mvc2.web.api;

import com.example.mvc2.exception.UserException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ApiExceptionController {

    @GetMapping("/api/members/{id}")
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

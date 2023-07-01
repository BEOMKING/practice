package com.example.mvc2.exhandler;

import lombok.Getter;

@Getter
public class ErrorResponse {
    private String message;
    private String code;
    private Integer status;

    public ErrorResponse(final String message, final String code, final Integer status) {
        this.message = message;
        this.code = code;
        this.status = status;
    }
}

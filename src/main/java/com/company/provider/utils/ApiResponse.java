package com.company.provider.utils;

import org.springframework.http.HttpStatus;

public class ApiResponse {
    private HttpStatus status;
    private String message;

    public ApiResponse(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}

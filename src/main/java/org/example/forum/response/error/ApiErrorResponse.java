package org.example.forum.response.error;


import lombok.Data;

@Data
public class ApiErrorResponse {
    private boolean success;
    private String message;

    public ApiErrorResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}

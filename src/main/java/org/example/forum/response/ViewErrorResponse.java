package org.example.forum.response;


import lombok.Data;

@Data
public class ViewErrorResponse {
    private boolean success;
    private String message;

    public ViewErrorResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}

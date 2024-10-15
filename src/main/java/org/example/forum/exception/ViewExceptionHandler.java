package org.example.forum.exception;


import org.example.forum.response.ViewErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ViewExceptionHandler {
    @ExceptionHandler(ValidateException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ViewErrorResponse todoException(Exception ex) {
        return new ViewErrorResponse(false, ex.getMessage());
    }
}

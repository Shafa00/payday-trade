package com.task.paydaytrade.handler;

import com.task.paydaytrade.model.ErrorResponseModel;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

@Log4j2
@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseModel> handle(Exception exception) {
        log.error("Exception thrown:", exception);
        if (exception instanceof MethodArgumentNotValidException ex) {
            var message = Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage();
            return generateErrorResponse(message);
        }
        return generateErrorResponse(exception.getMessage());
    }

    private ResponseEntity<ErrorResponseModel> generateErrorResponse(String message) {
        log.error(message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseModel(message));
    }
}

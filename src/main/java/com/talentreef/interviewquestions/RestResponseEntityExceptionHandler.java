package com.talentreef.interviewquestions;

import com.talentreef.interviewquestions.dto.ApiError;
import com.talentreef.interviewquestions.exceptions.ResourceNotFoundException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        List<String> errorList =
                ex.getBindingResult().getFieldErrors().stream()
                        .map(field -> field.getField() + ": " + field.getDefaultMessage())
                        .toList();

        ApiError apiError =
                new ApiError(HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(), errorList);

        return ResponseEntity.badRequest().body(apiError);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(
            @NonNull ResourceNotFoundException ex, WebRequest request) {

        ApiError apiError =
                new ApiError(
                        HttpStatus.NOT_FOUND.value(),
                        LocalDateTime.now(),
                        List.of(ex.getMessage()));

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }
}

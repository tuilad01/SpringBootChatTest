package com.example.testwebapp.controllers;

import com.example.testwebapp.common.ResponseObject;
import com.example.testwebapp.models.ValidationErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.method.MethodValidationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class CustomRestExceptionHandler {

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ResponseObject<List<ValidationErrorResponse>>> handleMethodValidationException(HandlerMethodValidationException ex) {
        List<ValidationErrorResponse> errorResponses = new ArrayList<>();

        ex.getAllValidationResults().forEach((error) -> {
            String fieldName = error.getMethodParameter().getParameterName();
            String errorMessage = error.getResolvableErrors().get(0).getDefaultMessage();
            errorResponses.add(new ValidationErrorResponse(fieldName, errorMessage));
        });

        return new ResponseEntity<>(new ResponseObject<>(false, "ERROR", "Validation failed", errorResponses), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

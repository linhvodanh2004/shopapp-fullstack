package com.project.pixelstore.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.pixelstore.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<?> handleUncaughtException(Exception ex){
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ApiResponse apiResponse = ApiResponse
                .builder()
                .success(false)
                .status(status.value())
                .message(ex.getMessage())
                .error(status.getReasonPhrase())
                .build();
        return ResponseEntity.status(status.value()).body(apiResponse);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<?> handleAppException(AppException ex){
        ErrorType errorType = ex.getErrorType();
        ApiResponse apiResponse = ApiResponse
                .builder()
                .success(false)
                .status(errorType.getStatus().value())
                .message(errorType.getLocalizedMessage())
                .error(errorType.getStatus().getReasonPhrase())
                .build();
        return ResponseEntity
                .status(errorType.getStatus())
                .body(apiResponse);
    }


    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        String jsonString = mapToJsonWithJackson(errors);

        ApiResponse apiResponse = ApiResponse
                .builder()
                .success(false)
                .status(status.value())
                .message(ex.getMessage())
                .error(jsonString)
                .build();
        return ResponseEntity.status(status.value()).body(apiResponse);
    }

    private String mapToJsonWithJackson(Map<String, String> map) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting map to JSON", e);
        }
    }
}

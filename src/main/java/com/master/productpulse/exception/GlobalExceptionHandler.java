package com.master.productpulse.exception;

import com.master.productpulse.utils.ApiResponse;
import com.master.productpulse.utils.LoggerUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(Exception ex, @ModelAttribute("traceId") String traceId) {
        // Handle the exception and use the traceId
        log.error("Exception occurred with traceId: {}", traceId, ex);

        ApiResponse<?> response = new ApiResponse<>(
                false,
                ex.getMessage() + ", Trace ID: " + traceId,
                null,
                LocalDateTime.now(),
                null,
                HttpStatus.INTERNAL_SERVER_ERROR
        );

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }


    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiResponse<?>> handleValidationException(ValidationException ex, HttpServletRequest request) {
        String traceID = LoggerUtil.generateLoggerId();
        ApiResponse<?> apiResponse = new ApiResponse<>(
                false,
                "Validation errors: " + ex.getMessage() + ", Trace ID: " + traceID,
                null,
                LocalDateTime.now(),
                request.getRequestURI(),
                HttpStatus.BAD_REQUEST
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

}

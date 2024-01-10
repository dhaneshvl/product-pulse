package com.master.productpulse.controller;

import com.master.productpulse.request.UserSignUpRequest;
import com.master.productpulse.service.UserService;
import com.master.productpulse.utils.ApiResponse;
import com.master.productpulse.utils.LoggerUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/onboard")
    public ApiResponse<?> signUp(@Valid @RequestBody UserSignUpRequest userSignUpRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) {
        final String traceID = LoggerUtil.generateLoggerId();
        try {
            if (bindingResult.hasErrors()) {
                // Construct a message with validation errors
                String validationErrorMessage = bindingResult.getFieldErrors().stream().map(error -> String.format("Field '%s': %s", error.getField(), error.getDefaultMessage())).collect(Collectors.joining(", "));

                throw new ValidationException(validationErrorMessage);
            }

            return userService.addUser(userSignUpRequest, httpServletRequest, traceID);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ApiResponse<>(false, ex.getMessage() + ", Trace ID: " + traceID, null, LocalDateTime.now(), httpServletRequest.getRequestURI(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ApiResponse<?> getAllUsers(HttpServletRequest httpServletRequest) {
        final String traceID = LoggerUtil.generateLoggerId();
        try {
            return userService.getAllUsers(httpServletRequest, traceID);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ApiResponse<>(false, ex.getMessage() + ", Trace ID: " + traceID, null, LocalDateTime.now(), httpServletRequest.getRequestURI(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{userId}")
    public ApiResponse<?> getUser(@PathVariable Long userId, HttpServletRequest httpServletRequest) {
        final String traceID = LoggerUtil.generateLoggerId();
        try {
            return userService.getUser(userId, httpServletRequest, traceID);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ApiResponse<>(false, ex.getMessage() + ", Trace ID: " + traceID, null, LocalDateTime.now(), httpServletRequest.getRequestURI(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{userId}")
    public ApiResponse<?> updateUser(@Valid @RequestBody UserSignUpRequest userSignUpRequest, BindingResult bindingResult, // Capture validation results
                                     @PathVariable Long userId, HttpServletRequest httpServletRequest) throws Exception {
        final String traceID = LoggerUtil.generateLoggerId();
        // Check for validation errors
        if (bindingResult.hasErrors()) {
            // Construct a message with validation errors
            String validationErrorMessage = bindingResult.getFieldErrors().stream().map(error -> String.format("Field '%s': %s", error.getField(), error.getDefaultMessage())).collect(Collectors.joining(", "));

            throw new ValidationException(validationErrorMessage);
        }

        return userService.updateUser(userId, userSignUpRequest, httpServletRequest, traceID);

    }


    @DeleteMapping("/{userId}")
    public ApiResponse<?> deleteUser(@PathVariable Long userId, HttpServletRequest httpServletRequest) {
        final String traceID = LoggerUtil.generateLoggerId();
        try {
            return userService.deleteUser(userId, httpServletRequest, traceID);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ApiResponse<>(false, ex.getMessage() + ", Trace ID: " + traceID, null, LocalDateTime.now(), httpServletRequest.getRequestURI(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
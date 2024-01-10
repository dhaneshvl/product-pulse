package com.master.productpulse.controller;


import com.master.productpulse.request.LoginRequest;
import com.master.productpulse.service.UserLoginService;
import com.master.productpulse.utils.ApiResponse;
import com.master.productpulse.utils.LoggerUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Timer;

@RequestMapping("/api/v1/login")
@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class LoginController {

    @Autowired
    UserLoginService userLoginService;

    @Autowired
    private HttpServletRequest request;

    Logger logger = LoggerFactory.getLogger(LoginController.class);

    @PostMapping
    public ApiResponse<?> login(@Valid @RequestBody LoginRequest loginRequest, HttpServletRequest httpServletRequest) {
        final String traceID = LoggerUtil.generateLoggerId();
        try {
            logger.info("Trace ID: {}, Request URI: {}, Request Body: {}",
                    traceID, httpServletRequest.getRequestURI(), loginRequest.toString());
            return userLoginService.login(loginRequest, httpServletRequest, traceID);
        } catch (Exception ex) {
            logger.error("An error occurred - Trace ID: {}, Request URI: {}, Request Body: {}, Error Message: {}",
                    traceID, httpServletRequest.getRequestURI(), loginRequest.toString(), ex.getMessage());
            return new ApiResponse<>(false, ex.getMessage()+", Trace ID: " + traceID, null, LocalDateTime.now(), httpServletRequest.getRequestURI(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

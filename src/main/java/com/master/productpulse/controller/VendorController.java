package com.master.productpulse.controller;

import com.master.productpulse.request.VendorCreationRequest;
import com.master.productpulse.service.VendorService;
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
@RequestMapping("api/v1/vendor")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class VendorController {

    @Autowired
    private VendorService storeService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @PostMapping("/onboard")
    public ApiResponse<?> createVendor(@Valid @RequestBody VendorCreationRequest storeCreationRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws Exception {
        final String traceID = LoggerUtil.generateLoggerId();

        // Check for validation errors
        if (bindingResult.hasErrors()) {
            // Construct a message with validation errors
            String validationErrorMessage = bindingResult.getFieldErrors().stream().map(error -> String.format("Field '%s': %s", error.getField(), error.getDefaultMessage())).collect(Collectors.joining(", "));

            throw new ValidationException(validationErrorMessage);
        }

        // Continue with the normal processing if no validation errors
        return storeService.createVendor(storeCreationRequest, httpServletRequest, traceID);
    }


    @GetMapping
    public ApiResponse<?> getAllVendors(HttpServletRequest httpServletRequest) {
        final String traceID = LoggerUtil.generateLoggerId();
        try {
            return storeService.getAllVendors(httpServletRequest, traceID);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ApiResponse<>(false, ex.getMessage() + ", Trace ID: " + traceID, null, LocalDateTime.now(), httpServletRequest.getRequestURI(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{vendorId}")
    public ApiResponse<?> getVendor(@PathVariable Integer vendorId, HttpServletRequest httpServletRequest) {
        final String traceID = LoggerUtil.generateLoggerId();
        try {
            return storeService.getVendor(vendorId, httpServletRequest, traceID);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ApiResponse<>(false, ex.getMessage() + ", Trace ID: " + traceID, null, LocalDateTime.now(), httpServletRequest.getRequestURI(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{vendorId}")
    public ApiResponse<?> updateVendor(@Valid @RequestBody VendorCreationRequest storeCreationRequest, BindingResult bindingResult, // Capture validation results
                                      @PathVariable Integer vendorId, HttpServletRequest httpServletRequest) throws Exception {
        final String traceID = LoggerUtil.generateLoggerId();

        // Check for validation errors
        if (bindingResult.hasErrors()) {
            // Construct a message with validation errors
            String validationErrorMessage = bindingResult.getFieldErrors().stream().map(error -> String.format("Field '%s': %s", error.getField(), error.getDefaultMessage())).collect(Collectors.joining(", "));

            throw new ValidationException(validationErrorMessage);
        }

        return storeService.updateVendor(vendorId, storeCreationRequest, httpServletRequest, traceID);

    }


    @DeleteMapping("/{vendorId}")
    public ApiResponse<?> deleteVendor(@PathVariable Integer vendorId, HttpServletRequest httpServletRequest) {
        final String traceID = LoggerUtil.generateLoggerId();
        try {
            return storeService.deleteVendor(vendorId, httpServletRequest, traceID);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ApiResponse<>(false, ex.getMessage() + ", Trace ID: " + traceID, null, LocalDateTime.now(), httpServletRequest.getRequestURI(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

package com.master.productpulse.controller;

import com.master.productpulse.request.SupplierRequest;
import com.master.productpulse.service.SupplierService;
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
@RequestMapping("api/v1/supplier")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @PostMapping("/create")
    public ApiResponse<?> createSupplier(@Valid @RequestBody SupplierRequest supplierRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws Exception {
        final String traceID = LoggerUtil.generateLoggerId();
        // Check for validation errors
        if (bindingResult.hasErrors()) {
            // Construct a message with validation errors
            String validationErrorMessage = bindingResult.getFieldErrors().stream().map(error -> String.format("Field '%s': %s", error.getField(), error.getDefaultMessage())).collect(Collectors.joining(", "));
            throw new ValidationException(validationErrorMessage);
        }

        // Continue with the normal processing if no validation errors
        return supplierService.createSupplier(supplierRequest, httpServletRequest, traceID);
    }

    @GetMapping
    public ApiResponse<?> getAllSuppliers(HttpServletRequest httpServletRequest) {
        final String traceID = LoggerUtil.generateLoggerId();
        try {
            return supplierService.getAllSuppliers(httpServletRequest, traceID);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ApiResponse<>(false, ex.getMessage() + ", Trace ID: " + traceID, null, LocalDateTime.now(), httpServletRequest.getRequestURI(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/{supplierId}")
    public ApiResponse<?> getCategory(@PathVariable Long supplierId, HttpServletRequest httpServletRequest) {
        final String traceID = LoggerUtil.generateLoggerId();
        try {
            return supplierService.getSupplier(supplierId, httpServletRequest, traceID);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ApiResponse<>(false, ex.getMessage() + ", Trace ID: " + traceID, null, LocalDateTime.now(), httpServletRequest.getRequestURI(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{supplierId}")
    public ApiResponse<?> updateSupplier(@Valid @RequestBody SupplierRequest supplierRequest, BindingResult bindingResult, // Capture validation results
                                         @PathVariable Long supplierId, HttpServletRequest httpServletRequest) throws Exception {
        final String traceID = LoggerUtil.generateLoggerId();

        // Check for validation errors
        if (bindingResult.hasErrors()) {
            // Construct a message with validation errors
            String validationErrorMessage = bindingResult.getFieldErrors().stream().map(error -> String.format("Field '%s': %s", error.getField(), error.getDefaultMessage())).collect(Collectors.joining(", "));

            throw new ValidationException(validationErrorMessage);
        }

        return supplierService.updateSupplier(supplierId, supplierRequest, httpServletRequest, traceID);

    }

    @DeleteMapping("/{supplierId}")
    public ApiResponse<?> deleteSupplier(@PathVariable Long supplierId, HttpServletRequest httpServletRequest) {
        final String traceID = LoggerUtil.generateLoggerId();
        try {
            return supplierService.deleteSupplier(supplierId, httpServletRequest, traceID);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ApiResponse<>(false, ex.getMessage() + ", Trace ID: " + traceID, null, LocalDateTime.now(), httpServletRequest.getRequestURI(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

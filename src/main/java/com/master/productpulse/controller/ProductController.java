package com.master.productpulse.controller;

import com.master.productpulse.request.ProductRequest;
import com.master.productpulse.service.ProductService;
import com.master.productpulse.utils.ApiResponse;
import com.master.productpulse.utils.LoggerUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/product")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @PostMapping("/create")
    public ApiResponse<?> createProduct(@Valid @RequestBody ProductRequest productRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws Exception {
        final String traceID = LoggerUtil.generateLoggerId();

        try {
            // Check for validation errors
            if (bindingResult.hasErrors()) {
                // Construct a message with validation errors
                String validationErrorMessage = bindingResult.getFieldErrors().stream().map(error -> String.format("Field '%s': %s", error.getField(), error.getDefaultMessage())).collect(Collectors.joining(", "));
                throw new ValidationException(validationErrorMessage);
            }
            return productService.createProduct(productRequest, httpServletRequest, traceID);
        } catch (Exception ex) {
            log.info("ProductController::createProduct. Exception: {}", ex.getMessage());
            return new ApiResponse<>(false, ex.getMessage() + ", Trace ID: " + traceID, null, LocalDateTime.now(), httpServletRequest.getRequestURI(), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping
    public ApiResponse<?> getAllProducts(HttpServletRequest httpServletRequest) {
        final String traceID = LoggerUtil.generateLoggerId();
        try {
            return productService.getAllProducts(httpServletRequest, traceID);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ApiResponse<>(false, ex.getMessage() + ", Trace ID: " + traceID, null, LocalDateTime.now(), httpServletRequest.getRequestURI(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/{productId}")
    public ApiResponse<?> getProduct(@PathVariable Long productId, HttpServletRequest httpServletRequest) {
        final String traceID = LoggerUtil.generateLoggerId();
        try {
            return productService.getProduct(productId, httpServletRequest, traceID);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ApiResponse<>(false, ex.getMessage() + ", Trace ID: " + traceID, null, LocalDateTime.now(), httpServletRequest.getRequestURI(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("supplier/{supplierId}")
    public ApiResponse<?> getProductsBySupplier(@PathVariable Long supplierId, HttpServletRequest httpServletRequest) {
        final String traceID = LoggerUtil.generateLoggerId();
        try {
            return productService.getProductsBySupplier(supplierId, httpServletRequest, traceID);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ApiResponse<>(false, ex.getMessage() + ", Trace ID: " + traceID, null, LocalDateTime.now(), httpServletRequest.getRequestURI(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{productId}")
    public ApiResponse<?> updateProduct(@Valid @RequestBody ProductRequest productRequest, BindingResult bindingResult, @PathVariable Long productId, HttpServletRequest httpServletRequest) throws Exception {
        final String traceID = LoggerUtil.generateLoggerId();

        if (bindingResult.hasErrors()) {
            String validationErrorMessage = bindingResult.getFieldErrors().stream().map(error -> String.format("Field '%s': %s", error.getField(), error.getDefaultMessage())).collect(Collectors.joining(", "));
            throw new ValidationException(validationErrorMessage);
        }
        return productService.updateProduct(productId, productRequest, httpServletRequest, traceID);

    }

    @DeleteMapping("/{productId}")
    public ApiResponse<?> deleteCategory(@PathVariable Long productId, HttpServletRequest httpServletRequest) {
        final String traceID = LoggerUtil.generateLoggerId();
        try {
            return productService.deleteProduct(productId, httpServletRequest, traceID);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ApiResponse<>(false, ex.getMessage() + ", Trace ID: " + traceID, null, LocalDateTime.now(), httpServletRequest.getRequestURI(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

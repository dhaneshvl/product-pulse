package com.master.productpulse.controller;

import com.master.productpulse.request.PurchaseEntryRequest;
import com.master.productpulse.service.PurchaseEntryService;
import com.master.productpulse.utils.ApiResponse;
import com.master.productpulse.utils.LoggerUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/purchase-entries")
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PurchaseEntryController {

    @Autowired
    private PurchaseEntryService purchaseEntryService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @PostMapping
    public ApiResponse<?> savePurchaseEntry(@RequestBody PurchaseEntryRequest purchaseEntryRequest, Model model) {
        final String traceID = LoggerUtil.generateLoggerId();
        model.addAttribute("traceId", traceID);
        try {
            log.info("PurchaseEntryController::savePurchaseEntry. Request received: {}", purchaseEntryRequest);
            return purchaseEntryService.savePurchaseEntry(purchaseEntryRequest, httpServletRequest, traceID);
        } catch (Exception ex) {
            log.info("PurchaseEntryController::savePurchaseEntry. Exception: {}", ex.getMessage());
            return new ApiResponse<>(false, ex.getMessage() + ", Trace ID: " + traceID, null, LocalDateTime.now(), httpServletRequest.getRequestURI(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ApiResponse<?> getAllPurchaseEntries(HttpServletRequest httpServletRequest, Model model) {
        final String traceID = LoggerUtil.generateLoggerId();
        model.addAttribute("traceId", traceID);
        try {
            return purchaseEntryService.getAllPurchaseEntries(httpServletRequest, traceID);
        } catch (Exception ex) {
            log.info("PurchaseEntryController::getAllPurchaseEntries. Exception: {}", ex.getMessage());
            return new ApiResponse<>(false, ex.getMessage() + ", Trace ID: " + traceID, null, LocalDateTime.now(), httpServletRequest.getRequestURI(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{purchaseEntryId}")
    public ApiResponse<?> getPurchaseEntry(@PathVariable Long purchaseEntryId , HttpServletRequest httpServletRequest, Model model) {
        final String traceID = LoggerUtil.generateLoggerId();
        model.addAttribute("traceId", traceID);
        try {
            return purchaseEntryService.getPurchaseEntry(purchaseEntryId,httpServletRequest, traceID);
        } catch (Exception ex) {
            log.info("PurchaseEntryController::getPurchaseEntry. Exception: {}", ex.getMessage());
            return new ApiResponse<>(false, ex.getMessage() + ", Trace ID: " + traceID, null, LocalDateTime.now(), httpServletRequest.getRequestURI(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

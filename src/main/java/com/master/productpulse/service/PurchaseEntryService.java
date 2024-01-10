package com.master.productpulse.service;

import com.master.productpulse.exception.ProductNotFoundException;
import com.master.productpulse.exception.SupplierNotFoundException;
import com.master.productpulse.mapper.PurchaseProductMapper;
import com.master.productpulse.model.Product;
import com.master.productpulse.model.PurchaseEntry;
import com.master.productpulse.model.PurchaseProduct;
import com.master.productpulse.model.Supplier;
import com.master.productpulse.repository.ProductRepository;
import com.master.productpulse.repository.PurchaseEntryRepository;
import com.master.productpulse.repository.SupplierRepository;
import com.master.productpulse.request.PurchaseEntryProductFields;
import com.master.productpulse.request.PurchaseEntryRequest;
import com.master.productpulse.utils.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PurchaseEntryService {

    @Autowired
    private PurchaseEntryRepository purchaseEntryRepository;
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public ApiResponse<?> savePurchaseEntry(PurchaseEntryRequest purchaseEntryRequest, HttpServletRequest httpServletRequest, String traceID) throws Exception {
        Supplier supplier = supplierRepository.findById(purchaseEntryRequest.getSupplierId())
                .orElseThrow(() -> new SupplierNotFoundException("Invalid Supplier"));

        Set<Long> productIds = purchaseEntryRequest.getPurchaseEntryProductFieldsList().stream()
                .map(PurchaseEntryProductFields::getProductId)
                .collect(Collectors.toSet());

        List<Product> productList = productRepository.findAllById(productIds);

        if (productList.size() != productIds.size()) {
            throw new ProductNotFoundException("Some products in the request are invalid or not found in the product list.");
        }

        List<PurchaseProduct> purchaseProductList = purchaseEntryRequest.getPurchaseEntryProductFieldsList().stream()
                .map(PurchaseProductMapper::mapToPurchaseProduct)
                .collect(Collectors.toList());

        PurchaseEntry purchaseEntry = new PurchaseEntry(
                purchaseEntryRequest.getSupplierId(),
                purchaseEntryRequest.getPurchaseNote(),
                LocalDateTime.now(),
                purchaseProductList
        );

        purchaseEntryRepository.save(purchaseEntry);

        updateProductStockCount(purchaseProductList);

        // Release resources and allow garbage collection
        supplier = null;
        productList = null;
        purchaseProductList = null;
        purchaseEntry = null;

        return new ApiResponse<>(true, "Purchase entry added successfully", null, LocalDateTime.now(),
                httpServletRequest.getRequestURI(), HttpStatus.OK);
    }

    private void updateProductStockCount(List<PurchaseProduct> purchaseProductList) {
        purchaseProductList.forEach(purchaseProduct -> {
            productRepository.findById(purchaseProduct.getProductId()).ifPresent(product -> {
                product.setStockCount(product.getStockCount() + purchaseProduct.getQuantity());
            });
        });
    }

    public ApiResponse<?> getAllPurchaseEntries(HttpServletRequest httpServletRequest, String traceID) throws Exception {
        return new ApiResponse<>(true, "success", purchaseEntryRepository.findAllPurchaseEntries(), LocalDateTime.now(), httpServletRequest.getRequestURI(), HttpStatus.OK);
    }

    public ApiResponse<?> getPurchaseEntry(Long purchaseEntryId,HttpServletRequest httpServletRequest, String traceID) throws Exception {

        PurchaseEntry purchaseEntry = purchaseEntryRepository .findById(purchaseEntryId)
                .orElseThrow(() -> new IllegalArgumentException("Purchase info not found"));

        return new ApiResponse<>(true, "success", purchaseEntry, LocalDateTime.now(), httpServletRequest.getRequestURI(), HttpStatus.OK);
    }


}

package com.master.productpulse.service;

import com.master.productpulse.dto.SupplierDTO;
import com.master.productpulse.exception.SupplierNotFoundException;
import com.master.productpulse.model.Supplier;
import com.master.productpulse.model.Product;
import com.master.productpulse.repository.SupplierRepository;
import com.master.productpulse.repository.ProductRepository;
import com.master.productpulse.request.SupplierRequest;
import com.master.productpulse.utils.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import com.master.productpulse.mapper.SupplierDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public ApiResponse<?> createSupplier(SupplierRequest supplierRequest, HttpServletRequest httpServletRequest, String traceID) throws Exception {
        supplierRepository.save(new Supplier(supplierRequest.getSupplierName(), supplierRequest.getAddress(), supplierRequest.getGstNo(), supplierRequest.getPhone(), LocalDateTime.now()));
        return new ApiResponse<>(true, "Supplier added successfully", null, LocalDateTime.now(), httpServletRequest.getRequestURI(), HttpStatus.CREATED);
    }

    public ApiResponse<?> getAllSuppliers(HttpServletRequest httpServletRequest, String traceID) throws Exception {
        List<SupplierDTO> supplierDTOList = supplierRepository.findAll().stream().map(SupplierDTOMapper::toDto).sorted(Comparator.comparing(SupplierDTO::getAddedDate).reversed()).collect(Collectors.toList());
        return new ApiResponse<>(true, "success", supplierDTOList, LocalDateTime.now(), httpServletRequest.getRequestURI(), HttpStatus.OK);

    }

    public ApiResponse<?> getSupplier(Long supplierId, HttpServletRequest httpServletRequest, String traceID) throws Exception {
        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new SupplierNotFoundException("Supplier not found"));
        return new ApiResponse<>(true, "success", SupplierDTOMapper.toDto(supplier), LocalDateTime.now(), httpServletRequest.getRequestURI(), HttpStatus.OK);

    }

    @Transactional
    public ApiResponse<?> updateSupplier(Long supplierId, SupplierRequest supplierRequest, HttpServletRequest httpServletRequest, String traceID) throws Exception {

        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new SupplierNotFoundException("Supplier not found"));

        // Check if the supplier name is being changed and if the new supplier name is taken
        if (!supplier.getSupplierName().equals(supplierRequest.getSupplierName()) && isSupplierNameTaken(supplierRequest.getSupplierName())) {
            return new ApiResponse<>(false, "Sorry, This supplier name has been taken", null, LocalDateTime.now(), httpServletRequest.getRequestURI(), HttpStatus.CONFLICT);
        }

        // Update the category details
        supplier.setSupplierName(supplierRequest.getSupplierName());
        supplier.setAddress(supplierRequest.getAddress());
        supplier.setGstNo(supplierRequest.getGstNo());
        supplier.setPhone(supplierRequest.getPhone());

        // Save the updated store
        supplierRepository.save(supplier);

        supplier = null;
        return new ApiResponse<>(true, "Supplier updated successfully", null, LocalDateTime.now(), httpServletRequest.getRequestURI(), HttpStatus.CREATED);
    }


//    public ApiResponse<?> deleteCategory(Long categoryId, HttpServletRequest httpServletRequest, String traceID) throws Exception {
//        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException("Category not found"));
//        categoryRepository.delete(category);
//        return new ApiResponse<>(true, String.format("Category, [%s] has been successfully deleted.", category.getCategoryName()), null, LocalDateTime.now(), httpServletRequest.getRequestURI(), HttpStatus.OK);
//
//    }

    public ApiResponse<?> deleteSupplier(Long supplierId, HttpServletRequest httpServletRequest, String traceID) throws Exception {
        Supplier supplier = supplierRepository.findById(supplierId).orElseThrow(() -> new SupplierNotFoundException("Category not found"));

        // Check if the supplier is associated with any products
        List<Product> productsWithSupplier = productRepository.findBySupplier(supplier);

        if (!productsWithSupplier.isEmpty()) {
            // If products are associated, build an error response
            List<String> productNames = productsWithSupplier.stream()
                    .map(Product::getProductName)
                    .collect(Collectors.toList());

            String errorMessage = String.format("Cannot delete supplier [%s] as it is associated with the following products: %s. Update or delete these products first.",
                    supplier.getSupplierName(), String.join(", ", productNames));

            return new ApiResponse<>(false, errorMessage, null, LocalDateTime.now(), httpServletRequest.getRequestURI(), HttpStatus.CONFLICT);
        }

        // No associated products, proceed with supplier deletion
        supplierRepository.delete(supplier);

        return new ApiResponse<>(true, String.format("Supplier [%s] has been successfully deleted.", supplier.getSupplierName()), null, LocalDateTime.now(), httpServletRequest.getRequestURI(), HttpStatus.OK);
    }


    public boolean isSupplierNameTaken(String supplierName) {
        return supplierRepository.findBySupplierNameIgnoreCase(supplierName).isPresent();
    }

}

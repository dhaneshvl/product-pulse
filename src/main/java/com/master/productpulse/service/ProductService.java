package com.master.productpulse.service;

import com.master.productpulse.dto.ProductDTO;
import com.master.productpulse.exception.DuplicateProductException;
import com.master.productpulse.exception.ProductNotFoundException;
import com.master.productpulse.model.Supplier;
import com.master.productpulse.model.Product;
import com.master.productpulse.repository.SupplierRepository;
import com.master.productpulse.repository.ProductRepository;
import com.master.productpulse.request.ProductRequest;
import com.master.productpulse.utils.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import com.master.productpulse.mapper.ProductDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Transactional
    public ApiResponse<?> createProduct(ProductRequest productRequest, HttpServletRequest httpServletRequest, String traceID) throws Exception {
        Optional<Supplier> supplier = supplierRepository.findById(productRequest.getSupplierId().describeConstable().orElseThrow(() -> new IllegalArgumentException("Invalid Supplier")));


        if (productRepository.existsByProductNameIgnoreCaseAndSupplier_Id(
                productRequest.getProductName(), productRequest.getSupplierId())) {
            throw new DuplicateProductException("Product with the same name already exists for the supplier");
        }


        productRepository.save(new Product(productRequest.getProductName(), supplier.get(), productRequest.getPrice(), productRequest.getDiscount(), LocalDateTime.now(),productRequest.getSgst(),productRequest.getCgst(),productRequest.getHsnCode()));
        return new ApiResponse<>(true, "Product added successfully", null, LocalDateTime.now(), httpServletRequest.getRequestURI(), HttpStatus.CREATED);
    }

    public ApiResponse<?> getAllProducts(HttpServletRequest httpServletRequest, String traceID) throws Exception {
        List<ProductDTO> productDTOList = productRepository.findAll().stream().map(ProductDTOMapper::toDto).sorted(Comparator.comparing(ProductDTO::getAddedDate).reversed()).collect(Collectors.toList());
        log.info("Product::getAllProducts, Response: {}", productDTOList);
        return new ApiResponse<>(true, "success", productDTOList, LocalDateTime.now(), httpServletRequest.getRequestURI(), HttpStatus.OK);

    }

    public ApiResponse<?> getProduct(Long productId, HttpServletRequest httpServletRequest, String traceID) throws Exception {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product not found"));
        log.info("ProductController::getProduct , Response : {}",ProductDTOMapper.toDto(product));
        return new ApiResponse<>(true, "success", ProductDTOMapper.toDto(product), LocalDateTime.now(), httpServletRequest.getRequestURI(), HttpStatus.OK);

    }

    public ApiResponse<?> getProductsBySupplier(Long categoryId, HttpServletRequest httpServletRequest, String traceID) throws Exception {
        Optional<Supplier> optionalCategory = supplierRepository.findById(categoryId);

        if (optionalCategory.isEmpty()) {
            throw new IllegalArgumentException("Invalid Supplier");
        }

        Supplier supplier = optionalCategory.get();
        List<Product> products = productRepository.findAllBySupplier(supplier);

        List<ProductDTO> productDTOs = products.stream()
                .map(ProductDTOMapper::toDto)
                .collect(Collectors.toList());

        log.info("ProductController::getProductsBySupplier , Response : {}", productDTOs);

        return new ApiResponse<>(
                true,
                "success",
                productDTOs,
                LocalDateTime.now(),
                httpServletRequest.getRequestURI(),
                HttpStatus.OK
        );
    }


    @Transactional
    public ApiResponse<?> updateProduct(Long productId, ProductRequest productRequest, HttpServletRequest httpServletRequest, String traceID) throws Exception {

        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product not found"));

        Optional<Supplier> category = supplierRepository.findById(productRequest.getSupplierId().describeConstable().orElseThrow(() -> new IllegalArgumentException("Invalid Category")));


        // Check if the product name is being changed and if the new product name is taken
        if (!product.getProductName().equals(productRequest.getProductName()) && isProductNameTaken(productRequest.getProductName())) {
            return new ApiResponse<>(false, "Sorry, This product name has been taken", null, LocalDateTime.now(), httpServletRequest.getRequestURI(), HttpStatus.CONFLICT);
        }

        // Update the product details
        product.setProductName(productRequest.getProductName());
        product.setSupplier(category.get());
        product.setDiscount(productRequest.getDiscount());
        product.setPrice(productRequest.getPrice());

        // Save the updated product
        productRepository.save(product);

        product = null;
        return new ApiResponse<>(true, "Product updated successfully", null, LocalDateTime.now(), httpServletRequest.getRequestURI(), HttpStatus.CREATED);
    }

    public ApiResponse<?> deleteProduct(Long productId, HttpServletRequest httpServletRequest, String traceID) throws Exception {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product not found"));
        productRepository.delete(product);
        return new ApiResponse<>(true, String.format("Product, [%s] has been successfully deleted.", product.getProductName()), null, LocalDateTime.now(), httpServletRequest.getRequestURI(), HttpStatus.OK);

    }

    public boolean isProductNameTaken(String productName) {
        return productRepository.findByProductNameIgnoreCase(productName).isPresent();
    }

}

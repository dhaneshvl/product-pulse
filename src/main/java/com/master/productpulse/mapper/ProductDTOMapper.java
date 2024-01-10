package com.master.productpulse.mapper;

import com.master.productpulse.dto.ProductDTO;
import com.master.productpulse.model.Product;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDTOMapper {

    public static ProductDTO toDto(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setSupplier(product.getSupplier());
        dto.setProductName(product.getProductName());
        dto.setPrice(product.getPrice());
        dto.setDiscount(product.getDiscount());
        dto.setAddedDate(product.getAddedDate());
        dto.setCgst(product.getCgst());
        dto.setSgst(product.getSgst());
        dto.setHsnCode(product.getHsnCode());
        return dto;
    }

    public static Product toEntity(ProductDTO dto) {
        Product product = new Product();
        product.setId(dto.getId());
        product.setSupplier(dto.getSupplier());
        product.setProductName(dto.getProductName());
        product.setPrice(dto.getPrice());
        product.setDiscount(dto.getDiscount());
        product.setAddedDate(dto.getAddedDate());
        product.setCgst(dto.getCgst());
        product.setSgst(dto.getSgst());
        product.setHsnCode(dto.getHsnCode());
        return product;
    }
}

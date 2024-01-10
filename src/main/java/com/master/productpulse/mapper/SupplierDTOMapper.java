package com.master.productpulse.mapper;

import com.master.productpulse.dto.SupplierDTO;
import com.master.productpulse.model.Supplier;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SupplierDTOMapper {

    public static SupplierDTO toDto(Supplier supplier) {
        SupplierDTO dto = new SupplierDTO();
        dto.setId(supplier.getId());
        dto.setSupplierName(supplier.getSupplierName());
        dto.setAddress(supplier.getAddress());
        dto.setGstNo(supplier.getGstNo());
        dto.setPhone(supplier.getPhone());
        dto.setAddedDate(supplier.getAddedDate());
        return dto;
    }

    public static Supplier toEntity(SupplierDTO dto) {
        Supplier supplier = new Supplier();
        supplier.setId(dto.getId());
        supplier.setSupplierName(dto.getSupplierName());
        supplier.setAddress(dto.getAddress());
        supplier.setGstNo(dto.getGstNo());
        supplier.setPhone(dto.getPhone());
        supplier.setAddedDate(dto.getAddedDate());
        return supplier;
    }
}

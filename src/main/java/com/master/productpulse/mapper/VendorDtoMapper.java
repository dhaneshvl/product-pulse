package com.master.productpulse.mapper;

import com.master.productpulse.dto.VendorDTO;
import com.master.productpulse.model.Vendor;

public class VendorDtoMapper {
    public static VendorDTO mapToDTO(Vendor store) {
        VendorDTO vendorDto = new VendorDTO();
        vendorDto.setId(store.getId());
        vendorDto.setPhone(store.getPhone());
        vendorDto.setName(store.getName());
        vendorDto.setPincode(store.getPincode());
        vendorDto.setAddress(store.getAddress());
        vendorDto.setGstNo(store.getGstNo());
        vendorDto.setProprietorName(store.getProprietorName());
        vendorDto.setAddedDate(store.getAddedDate());
        return vendorDto;
    }
}

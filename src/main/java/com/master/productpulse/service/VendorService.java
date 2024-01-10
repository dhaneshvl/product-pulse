package com.master.productpulse.service;

import com.master.productpulse.dto.VendorDTO;
import com.master.productpulse.exception.VendorNotFoundException;
import com.master.productpulse.mapper.VendorDtoMapper;
import com.master.productpulse.model.Vendor;
import com.master.productpulse.repository.VendorRepository;
import com.master.productpulse.request.VendorCreationRequest;
import com.master.productpulse.utils.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendorService {

    private VendorRepository vendorRepository;

    @Autowired
    public void setVendorRepository(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @Transactional
    public ApiResponse<?> createVendor(VendorCreationRequest vendorCreationRequest, HttpServletRequest httpServletRequest, String traceID) throws Exception {

        if (isVendorNameTaken(vendorCreationRequest.getName()))
            return new ApiResponse<>(false, "Sorry, This vendor name has been taken", null, LocalDateTime.now(), httpServletRequest.getRequestURI(), HttpStatus.ACCEPTED);
        if (isVendorPhoneTaken(vendorCreationRequest.getPhone()))
            return new ApiResponse<>(false, "Sorry, This phone number has been taken", null, LocalDateTime.now(), httpServletRequest.getRequestURI(), HttpStatus.ACCEPTED);

        Vendor newStore = vendorRepository.save(new Vendor(vendorCreationRequest.getName(), LocalDateTime.now(), vendorCreationRequest.getProprietorName(), vendorCreationRequest.getAddress(), vendorCreationRequest.getPincode(), vendorCreationRequest.getPhone(),vendorCreationRequest.getGstNo()));
        return new ApiResponse<>(true, "Vendor added successfully", VendorDtoMapper.mapToDTO(newStore), LocalDateTime.now(), httpServletRequest.getRequestURI(), HttpStatus.CREATED);

    }

    @Transactional
    public ApiResponse<?> updateVendor(Integer storeId, VendorCreationRequest vendorCreationRequest, HttpServletRequest httpServletRequest, String traceID) throws Exception {

        Vendor vendor = vendorRepository.findById(storeId)
                .orElseThrow(() -> new VendorNotFoundException("Vendor not found"));

        // Check if the vendor name is being changed and if the new name is taken
        if (!vendor.getName().equals(vendorCreationRequest.getName()) && isVendorNameTaken(vendorCreationRequest.getName())) {
            return new ApiResponse<>(false, "Sorry, This vendor name has been taken", null, LocalDateTime.now(), httpServletRequest.getRequestURI(), HttpStatus.CONFLICT);
        }

        // Check if the phone number is being changed and if the new phone number is taken
        if (!vendor.getPhone().equals(vendorCreationRequest.getPhone()) && isVendorPhoneTaken(vendorCreationRequest.getPhone())) {
            return new ApiResponse<>(false, "Sorry, This phone number has been taken", null, LocalDateTime.now(), httpServletRequest.getRequestURI(), HttpStatus.CONFLICT);
        }

        // Update the store details
        vendor.setPhone(vendorCreationRequest.getPhone());
        vendor.setName(vendorCreationRequest.getName());
        vendor.setProprietorName(vendorCreationRequest.getProprietorName());
        vendor.setPincode(vendorCreationRequest.getPincode());
        vendor.setAddress(vendorCreationRequest.getAddress());
        vendor.setGstNo(vendorCreationRequest.getGstNo());

        // Save the updated vendor
        vendorRepository.save(vendor);

        vendor = null;
        return new ApiResponse<>(true, "Vendor updated successfully", null, LocalDateTime.now(), httpServletRequest.getRequestURI(), HttpStatus.CREATED);
    }

    public ApiResponse<?> getAllVendors(HttpServletRequest httpServletRequest, String traceID) throws Exception {
        List<VendorDTO> vendorDTOList = vendorRepository.findAll()
                .stream()
                .map(VendorDtoMapper::mapToDTO)
                .sorted(Comparator.comparing(VendorDTO::getAddedDate).reversed())
                .collect(Collectors.toList());
        return new ApiResponse<>(true, "success", vendorDTOList, LocalDateTime.now(), httpServletRequest.getRequestURI(), HttpStatus.OK);

    }

    public ApiResponse<?> getVendor(Integer vendorId, HttpServletRequest httpServletRequest, String traceID) throws Exception {
        Vendor vendor = vendorRepository.findById(vendorId).orElseThrow(() -> new VendorNotFoundException("Vendor not found"));
        return new ApiResponse<>(true, "success", VendorDtoMapper.mapToDTO(vendor), LocalDateTime.now(), httpServletRequest.getRequestURI(), HttpStatus.OK);

    }

    public ApiResponse<?> deleteVendor(Integer vendorId, HttpServletRequest httpServletRequest, String traceID) throws Exception {
        Vendor vendor = vendorRepository.findById(vendorId).orElseThrow(() -> new VendorNotFoundException("Vendor not found"));
        vendorRepository.delete(vendor);
        return new ApiResponse<>(true, String.format("Vendor, [%s] has been successfully deleted.", vendor.getName()), null, LocalDateTime.now(), httpServletRequest.getRequestURI(), HttpStatus.OK);

    }

    public boolean isVendorNameTaken(String vendorName) {
        return vendorRepository.findByName(vendorName).isPresent();
    }

    public boolean isVendorPhoneTaken(String vendorPhone) {
        return vendorRepository.findByPhone(vendorPhone).isPresent();
    }

}

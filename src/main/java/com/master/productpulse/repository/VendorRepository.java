package com.master.productpulse.repository;

import com.master.productpulse.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VendorRepository extends JpaRepository<Vendor,Integer> {

    Optional<Vendor> findByName(String name);

    Optional<Vendor> findByPhone(String phone);
}

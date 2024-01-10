package com.master.productpulse.repository;

import com.master.productpulse.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier,Long> {
    Optional<Supplier> findBySupplierNameIgnoreCase(String supplierName);
}

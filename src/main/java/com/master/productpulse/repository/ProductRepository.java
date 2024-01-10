package com.master.productpulse.repository;

import com.master.productpulse.model.Supplier;
import com.master.productpulse.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    Optional<Product> findByProductNameIgnoreCase(String productName);

    List<Product> findBySupplier(Supplier supplier);

    List<Product> findAllBySupplier(Supplier supplier);

    boolean existsByProductNameIgnoreCaseAndSupplier_Id(String productName, Long supplierId);

}

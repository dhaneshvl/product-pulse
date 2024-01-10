package com.master.productpulse.repository;

import com.master.productpulse.model.PurchaseEntry;
import com.master.productpulse.response.PurchaseEntryDetailsResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PurchaseEntryRepository extends JpaRepository<PurchaseEntry, Long> {

    @Query("SELECT new com.master.productpulse.response.PurchaseEntryDetailsResponse(pe.id, pe.purchaseNote, pe.purchaseDate, s.supplierName) " +
            "FROM PurchaseEntry pe " +
            "JOIN Supplier s ON pe.supplierId = s.id " +
            "ORDER BY pe.purchaseDate DESC")
    List<PurchaseEntryDetailsResponse> findAllPurchaseEntries();

}

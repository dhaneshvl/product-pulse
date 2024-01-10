package com.master.productpulse.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class PurchaseEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long supplierId;

    @Column(nullable = false)
    private String purchaseNote;

    @Column(nullable = false)
    private LocalDateTime purchaseDate;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "purchase_entry_id")
    private List<PurchaseProduct> products;

    @Transient
    private String supplierName;

    public PurchaseEntry(Long supplierId, String purchaseNote, LocalDateTime purchaseDate, List<PurchaseProduct> products) {
        this.supplierId = supplierId;
        this.purchaseNote = purchaseNote;
        this.products = products;
        this.purchaseDate = purchaseDate;
    }
}

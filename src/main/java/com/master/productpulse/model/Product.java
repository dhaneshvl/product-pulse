package com.master.productpulse.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    private Double price;

    private BigDecimal discount;

    private LocalDateTime addedDate;

    private BigDecimal sgst;

    private BigDecimal cgst;

    private String hsnCode;

    private Integer stockCount;

    public Product(String productName, Supplier supplier, Double price, BigDecimal discount, LocalDateTime addedDate, BigDecimal sgst, BigDecimal cgst, String hsnCode) {
        this.productName = productName;
        this.supplier = supplier;
        this.price = price;
        this.discount = discount;
        this.addedDate = addedDate;
        this.sgst = sgst;
        this.cgst = cgst;
        this.hsnCode = hsnCode;
    }

}

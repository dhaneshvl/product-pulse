package com.master.productpulse.model;

import com.master.productpulse.enums.ProductType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
@Entity
@Table(name = "store_deliveries")
public class StoreDelivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int storeId;

    private String productId;

    private LocalDateTime deliveryDate;

    private int quantityDelivered;

    private Double price;

    @Enumerated(EnumType.STRING)
    private ProductType productType;

    private Long userId;

    private int discount;


}

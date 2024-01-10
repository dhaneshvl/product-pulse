package com.master.productpulse.model;

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
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String supplierName;
    private String address;
    private String gstNo;
    private String phone;
    private LocalDateTime addedDate;

    public Supplier(String supplierName, String address, String gstNo, String phone , LocalDateTime addedDate) {
        this.supplierName = supplierName;
        this.address = address;
        this.gstNo = gstNo;
        this.phone = phone;
        this.addedDate=addedDate;
    }

}

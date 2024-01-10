package com.master.productpulse.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
@AllArgsConstructor
@Builder
@Entity
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private LocalDateTime addedDate;

    private String proprietorName;

    private String address;

    private int pincode;

    private String phone;

    private String gstNo;

    public Vendor(String name, LocalDateTime addedDate, String proprietorName, String address, int pincode, String phone, String  gstNo) {
        this.name = name;
        this.addedDate = addedDate;
        this.proprietorName = proprietorName;
        this.address = address;
        this.pincode = pincode;
        this.phone = phone;
        this.gstNo = gstNo;
    }
}

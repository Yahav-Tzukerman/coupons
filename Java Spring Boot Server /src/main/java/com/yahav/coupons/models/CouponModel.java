package com.yahav.coupons.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponModel {
    private long id;
    private String code;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private String category;
    private int amount;
    private float price;
    private long companyId;
    private String companyName;
    private String imageUrl;
}

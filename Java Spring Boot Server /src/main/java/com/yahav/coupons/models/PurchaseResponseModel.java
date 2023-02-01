package com.yahav.coupons.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseResponseModel {
    private long id;
    private long couponId;
    private long userId;
    private LocalDateTime purchaseTimestamp;
    private int amount;
    private float totalPrice;
}

package com.yahav.coupons.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseRequestModel {
    private long couponId;
    private long userId;
    private int amount;
}

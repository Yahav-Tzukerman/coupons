package com.yahav.coupons.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "purchases")
public class PurchaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private long id;

    @ManyToOne
    private CouponEntity coupon;

    @ManyToOne
    private UserEntity user;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime purchaseTimestamp;

    @Column(name = "amount", nullable = false)
    private int amount;

    @Column(name = "total_price", nullable = false)
    private float totalPrice;

}

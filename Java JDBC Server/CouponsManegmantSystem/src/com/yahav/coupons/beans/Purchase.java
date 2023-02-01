package com.yahav.coupons.beans;

import java.sql.Timestamp;

public class Purchase {
    private int purchaseId;
    private String username;
    private String couponCode;
    private Timestamp purchaseDate;
    private int amount;
    private float totalPrice;

    public Purchase() {
    }

    public Purchase(int purchaseId, String username, String couponCode, Timestamp purchaseDate, int amount, float totalPrice) {
        this.purchaseId = purchaseId;
        this.username = username;
        this.couponCode = couponCode;
        this.purchaseDate = purchaseDate;
        this.amount = amount;
        this.totalPrice = totalPrice;
    }

    public Purchase(String username, String couponCode, Timestamp purchaseDate, int amount, float totalPrice) {
        this.username = username;
        this.couponCode = couponCode;
        this.purchaseDate = purchaseDate;
        this.amount = amount;
        this.totalPrice = totalPrice;
    }

    public Purchase(String username, String couponCode, int amount) {
        this.username = username;
        this.couponCode = couponCode;
        this.amount = amount;
    }

    public int getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(int purchaseId) {
        this.purchaseId = purchaseId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public Timestamp getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Timestamp purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "purchaseId=" + purchaseId +
                ", username='" + username + '\'' +
                ", couponCode='" + couponCode + '\'' +
                ", purchaseDate=" + purchaseDate +
                ", amount=" + amount +
                ", totalPrice=" + totalPrice +
                '}';
    }
}

package com.yahav.coupons.controllers;

import com.yahav.coupons.beans.Purchase;
import com.yahav.coupons.exceptions.ServerException;
import com.yahav.coupons.logics.PurchaseLogic;

import java.util.ArrayList;
import java.util.List;

public class PurchaseController {
    private static final PurchaseLogic purchaseLogic = new PurchaseLogic();

    public void updatePurchase(Purchase purchase) {
        try {
            int result = purchaseLogic.updatePurchase(purchase);
            if (result != 0) {
                System.out.println(purchase.getUsername() + " ," + purchase.getCouponCode() + ": Purchase was updated successfully!");
            }
        } catch (ServerException e) {
            System.out.println(e.getMessage());
        }
    }

    public Purchase getPurchase(String username, String couponCode) {
        Purchase purchase = null;
        try {
            purchase = purchaseLogic.getPurchase(username, couponCode);
        } catch (ServerException e) {
            System.out.println(e.getMessage());
        }
        return purchase;
    }

    public Purchase getPurchase(int purchaseId) {
        Purchase purchase = null;
        try {
            purchase = purchaseLogic.getPurchase(purchaseId);
        } catch (ServerException e) {
            System.out.println(e.getMessage());
        }
        return purchase;
    }

    public List<Purchase> getUserPurchasesByMaxPrice(String username, float maxPrice) {
        List<Purchase> purchases = new ArrayList<>();
        try {
            purchases = purchaseLogic.getUserPurchasesByMaxPrice(username, maxPrice);
        } catch (ServerException e) {
            System.out.println(e.getMessage());
        }
        return purchases;
    }

    public List<Purchase> getCompanyPurchases(String companyName) {
        List<Purchase> purchases = new ArrayList<>();
        try {
            purchases = purchaseLogic.getCompanyPurchases(companyName);
        } catch (ServerException e) {
            System.out.println(e.getMessage());
        }
        return purchases;
    }

    public List<Purchase> getCouponPurchases(String couponCode) {
        List<Purchase> purchases = new ArrayList<>();
        try {
            purchases = purchaseLogic.getCouponPurchases(couponCode);
        } catch (ServerException e) {
            System.out.println(e.getMessage());
        }
        return purchases;
    }

    public List<Purchase> getUserPurchases(String username) {
        List<Purchase> purchases = new ArrayList<>();
        try {
            purchases = purchaseLogic.getUserPurchases(username);
        } catch (ServerException e) {
            System.out.println(e.getMessage());
        }
        return purchases;
    }

    public List<Purchase> getPurchases() {
        List<Purchase> purchases = new ArrayList<>();
        try {
            purchases = purchaseLogic.getPurchases();
        } catch (ServerException e) {
            System.out.println(e.getMessage());
        }
        return purchases;
    }

    public void deletePurchase(Purchase purchase) {
        deletePurchase(purchase.getUsername(), purchase.getCouponCode());
    }

    public void deletePurchase(String username, String couponCode) {
        try {
            int result = purchaseLogic.deletePurchases(username, couponCode);
            if (result != 0) {
                System.out.println(username + " ," + couponCode + ": Purchase was deleted successfully");
            }
        } catch (ServerException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deletePurchase(int purchaseId) {
        try {
            int result = purchaseLogic.deletePurchase(purchaseId);
            if (result != 0) {
                System.out.println(purchaseId + ": Purchase was deleted successfully");
            }
        } catch (ServerException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addPurchase(Purchase purchase) {
        try {
            int purchaseId = purchaseLogic.addPurchase(purchase);
            if (purchaseId > 0) {
                System.out.println(purchase.getUsername() + " ," + purchase.getCouponCode() + ": Purchase was added successfully");
            }
        } catch (ServerException e) {
            System.out.println(e.getMessage());
        }
    }
}
package com.yahav.coupons.controllers;

import com.yahav.coupons.exceptions.ServerException;
import com.yahav.coupons.logics.*;

public class ClearContent {
    private static final CompanyLogic companyLogic = new CompanyLogic();
    private static final CouponLogic couponLogic = new CouponLogic();
    private static final CustomerLogic customerLogic = new CustomerLogic();
    private static final PurchaseLogic purchaseLogic = new PurchaseLogic();
    private static final UserLogic userLogic = new UserLogic();

    public static void deleteAllContent() {
        try {
            companyLogic.clearContent();
            couponLogic.clearContent();
            customerLogic.clearContent();
            purchaseLogic.clearContent();
            userLogic.clearContent();
        } catch (ServerException e) {
            System.out.println(e.getMessage());
        }

    }
}

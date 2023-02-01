package com.yahav.coupons.controllers;

import com.yahav.coupons.beans.Coupon;
import com.yahav.coupons.enums.Category;
import com.yahav.coupons.enums.ErrorType;
import com.yahav.coupons.exceptions.ServerException;
import com.yahav.coupons.logics.CouponLogic;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CouponController {
    private static final CouponLogic couponLogic = new CouponLogic();

    public void updateCoupon(Coupon coupon) {
        try {
            int result = couponLogic.updateCoupon(coupon);
            if (result != 0) {
                System.out.println(coupon.getCode() + ": Coupon was updated successfully");
            }
        } catch (ServerException e) {
            System.out.println(e.getMessage());
        }
    }

    public Coupon getCoupon(String couponCode) {
        Coupon coupon = null;
        try {
            coupon = couponLogic.getCoupon(couponCode);
            if(coupon == null){
                throw new ServerException(ErrorType.COUPON_DOES_NOT_EXIST);
            }
        } catch (ServerException e) {
            System.out.println(e.getMessage());
        }
        return coupon;
    }

    public Coupon getCoupon(int couponId) {
        Coupon coupon = null;
        try {
            coupon = couponLogic.getCoupon(couponId);
            if(coupon == null){
                throw new ServerException(ErrorType.COUPON_DOES_NOT_EXIST);
            }
        } catch (ServerException e) {
            System.out.println(e.getMessage());
        }
        return coupon;
    }

    public List<Coupon> getCouponsByCategory(Category category) {
        List<Coupon> coupons = new ArrayList<>();
        try {
            coupons = couponLogic.getCouponsByCategory(category);
        } catch (ServerException e) {
            System.out.println(e.getMessage());
        }
        return coupons;
    }

    public List<Coupon> getCoupons() {
        List<Coupon> coupons = new ArrayList<>();
        try {
            coupons = couponLogic.getCoupons();
        } catch (ServerException e) {
            System.out.println(e.getMessage());
        }
        return coupons;
    }

    public void deleteCoupon(Coupon coupon) {
        deleteCoupon(coupon.getCode());
        if (coupon.getEndDate().toLocalDate().isBefore(LocalDate.now())) {
            System.out.println("Expired coupon was deleted successfully ");
        }
    }

    public void deleteCoupon(String couponCode) {
        try {
            int result = couponLogic.deleteCoupon(couponCode);
            if (result != 0) {
                System.out.println(couponCode + ": Coupon was deleted successfully");
            }
        } catch (ServerException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteCoupon(int couponId) {
        try {
            int result = couponLogic.deleteCoupon(couponId);
            if (result != 0) {
                System.out.println(couponId + ": Coupon was deleted successfully");
            }
        } catch (ServerException e) {
            System.out.println(e.getMessage());
        }
    }

    public Integer addCoupon(Coupon coupon) {
        Integer couponId = null;
        try {
            couponId = couponLogic.addCoupon(coupon);
            if (couponId > 0) {
                System.out.println(coupon.getCode() + ": Coupon was added successfully");
            }
        } catch (ServerException e) {
            System.out.println(e.getMessage());
        }
        return couponId;
    }

    public Integer addInvalidCouponForJobTest(Coupon coupon) {
        Integer couponId = null;
        try {
            couponId = couponLogic.addInvalidCouponForJobTest(coupon);
            if (couponId > 0) {
                System.out.println(coupon.getCode() + ": invalid Coupon was added successfully");
            }
        } catch (ServerException e) {
            System.out.println(e.getMessage());
        }
        return couponId;
    }

}

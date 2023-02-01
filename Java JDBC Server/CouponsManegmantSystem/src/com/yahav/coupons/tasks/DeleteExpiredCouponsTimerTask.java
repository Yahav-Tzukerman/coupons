package com.yahav.coupons.tasks;

import com.yahav.coupons.beans.Coupon;
import com.yahav.coupons.controllers.CouponController;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.TimerTask;

public class DeleteExpiredCouponsTimerTask extends TimerTask {
    private static final CouponController couponController = new CouponController();

    @Override
    public void run() {
        Date currentDate = Date.valueOf(LocalDate.now());
        List<Coupon> couponList = couponController.getCoupons();
        for (Coupon coupon : couponList) {
            if (coupon.getEndDate().before(currentDate)) {
                couponController.deleteCoupon(coupon);
            }
        }
    }
}

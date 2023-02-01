package com.yahav.coupons.tasks;

import com.yahav.coupons.models.CouponModel;
import com.yahav.coupons.services.CouponService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class DeleteExpiredCouponsTimerTask extends TimerTask {

    private static final long dayPeriod = 86_400_000;
    private final CouponService couponService;

    @Autowired
    public DeleteExpiredCouponsTimerTask(@Lazy CouponService couponService) {
        this.couponService = couponService;
    }

    @PostConstruct
    public void initTask() {
        Timer timer = new Timer();
        timer.schedule(this, getExecutionDate(), dayPeriod);
    }

    private static Date getExecutionDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    @SneakyThrows
    @Override
    public void run() {
        List<CouponModel> expiredCoupons = couponService.getExpiredCoupons();
        for (CouponModel coupon : expiredCoupons) {
            couponService.removeCoupon(coupon.getId());
        }
    }
}

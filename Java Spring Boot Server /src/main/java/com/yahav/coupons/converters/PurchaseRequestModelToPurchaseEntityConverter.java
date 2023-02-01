package com.yahav.coupons.converters;

import com.yahav.coupons.entities.PurchaseEntity;
import com.yahav.coupons.models.PurchaseRequestModel;
import com.yahav.coupons.services.CouponService;
import com.yahav.coupons.services.UserService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PurchaseRequestModelToPurchaseEntityConverter implements Converter<PurchaseRequestModel, PurchaseEntity> {

    private final UserService userService;
    private final CouponService couponService;

    @Autowired
    public PurchaseRequestModelToPurchaseEntityConverter(@Lazy UserService userService, @Lazy CouponService couponService) {
        this.userService = userService;
        this.couponService = couponService;
    }

    @SneakyThrows
    @Override
    public PurchaseEntity convert(PurchaseRequestModel purchaseRequestModel) {
        PurchaseEntity purchaseEntity = new PurchaseEntity();
        purchaseEntity.setCoupon(couponService.getCouponEntity(purchaseRequestModel.getCouponId()));
        purchaseEntity.setUser(userService.getUserEntity(purchaseRequestModel.getUserId()));
        purchaseEntity.setAmount(purchaseRequestModel.getAmount());
        return purchaseEntity;
    }
}

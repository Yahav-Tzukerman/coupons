package com.yahav.coupons.converters;

import com.sun.istack.NotNull;
import com.yahav.coupons.entities.CouponEntity;
import com.yahav.coupons.models.CouponModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CouponEntityToCouponModelConverter implements Converter<CouponEntity, CouponModel> {

    @Override
    public CouponModel convert(@NotNull CouponEntity couponEntity) {
        CouponModel couponModel = new CouponModel();
        couponModel.setId(couponEntity.getId());
        couponModel.setCode(couponEntity.getCode());
        couponModel.setTitle(couponEntity.getTitle());
        couponModel.setDescription(couponEntity.getDescription());
        couponModel.setStartDate(couponEntity.getStartDate());
        couponModel.setEndDate(couponEntity.getEndDate());
        couponModel.setCategory(couponEntity.getCategory().getName());
        couponModel.setAmount(couponEntity.getAmount());
        couponModel.setPrice(couponEntity.getPrice());
        couponModel.setCompanyId(couponEntity.getCompany().getId());
        couponModel.setCompanyName(couponEntity.getCompany().getName());
        couponModel.setImageUrl(couponEntity.getImageUrl());
        return couponModel;
    }
}

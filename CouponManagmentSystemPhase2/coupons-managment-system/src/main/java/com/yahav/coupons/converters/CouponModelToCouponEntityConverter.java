package com.yahav.coupons.converters;

import com.sun.istack.NotNull;
import com.yahav.coupons.entities.CouponEntity;
import com.yahav.coupons.models.CouponModel;
import com.yahav.coupons.services.CategoryService;
import com.yahav.coupons.services.CompanyService;
import com.yahav.coupons.services.PurchaseService;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CouponModelToCouponEntityConverter implements Converter<CouponModel, CouponEntity> {

    private final CategoryService categoryService;
    private final CompanyService companyService;
    private final PurchaseService purchaseService;

    public CouponModelToCouponEntityConverter(@Lazy CategoryService categoryService, @Lazy CompanyService companyService, @Lazy PurchaseService purchaseService) {
        this.categoryService = categoryService;
        this.companyService = companyService;
        this.purchaseService = purchaseService;
    }

    @SneakyThrows
    @Override
    public CouponEntity convert(@NotNull CouponModel couponModel) {
        CouponEntity couponEntity = new CouponEntity();
        couponEntity.setId(couponModel.getId());
        couponEntity.setCode(couponModel.getCode());
        couponEntity.setTitle(couponModel.getTitle());
        couponEntity.setDescription(couponModel.getDescription());
        couponEntity.setStartDate(couponModel.getStartDate());
        couponEntity.setEndDate(couponModel.getEndDate());
        couponEntity.setCategory(categoryService.getCategoryEntityByName(couponModel.getCategory()));
        couponEntity.setAmount(couponModel.getAmount());
        couponEntity.setPrice(couponModel.getPrice());
        couponEntity.setImageUrl(couponModel.getImageUrl());
        couponEntity.setCompany(companyService.getCompanyEntity(couponModel.getCompanyId()));
        if (couponModel.getId() != 0) {
            couponEntity.setPurchases(purchaseService.getPurchaseEntitiesByCouponId(couponModel.getId()));
        }
        return couponEntity;
    }
}

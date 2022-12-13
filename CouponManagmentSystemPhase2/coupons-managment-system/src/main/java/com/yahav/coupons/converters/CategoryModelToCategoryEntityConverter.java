package com.yahav.coupons.converters;

import com.sun.istack.NotNull;
import com.yahav.coupons.entities.CategoryEntity;
import com.yahav.coupons.models.CategoryModel;
import com.yahav.coupons.services.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CategoryModelToCategoryEntityConverter implements Converter<CategoryModel, CategoryEntity> {

    private final CouponService couponService;

    @Autowired
    public CategoryModelToCategoryEntityConverter(@Lazy CouponService couponService) {
        this.couponService = couponService;
    }

    @Override
    public CategoryEntity convert(@NotNull CategoryModel categoryModel) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(categoryModel.getId());
        categoryEntity.setName(categoryModel.getName());
        categoryEntity.setCoupons(couponService.getCouponEntitiesByCategory(categoryModel.getId()));
        return categoryEntity;
    }
}

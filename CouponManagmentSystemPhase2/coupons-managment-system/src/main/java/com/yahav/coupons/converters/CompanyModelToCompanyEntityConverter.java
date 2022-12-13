package com.yahav.coupons.converters;


import com.sun.istack.NotNull;
import com.yahav.coupons.entities.CompanyEntity;
import com.yahav.coupons.models.CompanyModel;
import com.yahav.coupons.services.CouponService;
import com.yahav.coupons.services.UserService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CompanyModelToCompanyEntityConverter implements Converter<CompanyModel, CompanyEntity> {

    private final CouponService couponService;
    private final UserService userService;

    @Autowired
    public CompanyModelToCompanyEntityConverter(@Lazy CouponService couponService, @Lazy UserService userService) {
        this.couponService = couponService;
        this.userService = userService;
    }

    @SneakyThrows
    @Override
    public CompanyEntity convert(@NotNull CompanyModel companyModel) {
        CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.setId(companyModel.getId());
        companyEntity.setName(companyModel.getName());
        companyEntity.setPhone(companyModel.getPhone());
        companyEntity.setAddress(companyModel.getAddress());
        companyEntity.setFoundationDate(companyModel.getFoundationDate());
        companyEntity.setLogo(companyModel.getLogo());
        if (companyEntity.getId() != 0) {
            companyEntity.setCoupons(couponService.getCompanyCouponsEntities(companyModel.getId()));
            companyEntity.setUsers(userService.getCompanyUsersEntities(companyModel.getId()));
        }
        return companyEntity;
    }
}

package com.yahav.coupons.converters;

import com.sun.istack.NotNull;
import com.yahav.coupons.entities.UserEntity;
import com.yahav.coupons.models.UserRequestModel;
import com.yahav.coupons.services.CompanyService;
import com.yahav.coupons.services.PurchaseService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserRequestModelToUserEntityConverter implements Converter<UserRequestModel, UserEntity> {

    private final CompanyService companyService;
    private final PurchaseService purchaseService;

    @Autowired
    public UserRequestModelToUserEntityConverter(@Lazy CompanyService companyService, @Lazy PurchaseService purchaseService) {
        this.companyService = companyService;
        this.purchaseService = purchaseService;
    }

    @SneakyThrows
    @Override
    public UserEntity convert(@NotNull UserRequestModel userRequestModel) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userRequestModel.getId());
        userEntity.setUsername(userRequestModel.getUsername());
        userEntity.setPassword(userRequestModel.getPassword());
        userEntity.setFirstName(userRequestModel.getFirstName());
        userEntity.setLastName(userRequestModel.getLastName());
        userEntity.setRole(userRequestModel.getRole());
        userEntity.setPhone(userRequestModel.getPhone());
        if (userRequestModel.getCompanyId() != null) {
            userEntity.setCompany(companyService.getCompanyEntity(userRequestModel.getCompanyId()));
        }
        if (userEntity.getId() != 0) {
            userEntity.setPurchases(purchaseService.getPurchaseEntitiesByUserId(userRequestModel.getId()));
        }
        return userEntity;
    }
}

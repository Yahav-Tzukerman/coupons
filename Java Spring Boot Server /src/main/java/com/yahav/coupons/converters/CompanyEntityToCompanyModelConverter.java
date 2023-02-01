package com.yahav.coupons.converters;

import com.sun.istack.NotNull;
import com.yahav.coupons.entities.CompanyEntity;
import com.yahav.coupons.models.CompanyModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CompanyEntityToCompanyModelConverter implements Converter<CompanyEntity, CompanyModel> {

    public CompanyEntityToCompanyModelConverter() {
    }

    @Override
    public CompanyModel convert(@NotNull CompanyEntity companyEntity) {
        CompanyModel companyModel = new CompanyModel();
        companyModel.setId(companyEntity.getId());
        companyModel.setName(companyEntity.getName());
        companyModel.setPhone(companyEntity.getPhone());
        companyModel.setAddress(companyEntity.getAddress());
        companyModel.setFoundationDate(companyEntity.getFoundationDate());
        companyModel.setLogo(companyEntity.getLogo());
        return companyModel;
    }
}

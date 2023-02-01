package com.yahav.coupons.converters;

import com.sun.istack.NotNull;
import com.yahav.coupons.entities.CustomerEntity;
import com.yahav.coupons.models.CustomerResponseModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CustomerEntityToCustomerResponseModelConverter implements Converter<CustomerEntity, CustomerResponseModel> {

    @Override
    public CustomerResponseModel convert(@NotNull CustomerEntity customerEntity) {
        CustomerResponseModel customerResponseModel = new CustomerResponseModel();
        customerResponseModel.setId(customerEntity.getId());
        customerResponseModel.setAddress(customerEntity.getAddress());
        customerResponseModel.setAmountOfChildren(customerEntity.getAmountOfChildren());
        customerResponseModel.setBirthDate(customerEntity.getBirthDate());
        customerResponseModel.setGender(customerEntity.getGender());
        customerResponseModel.setMaritalStatus(customerEntity.getMaritalStatus());
        return customerResponseModel;
    }
}

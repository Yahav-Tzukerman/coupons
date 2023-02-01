package com.yahav.coupons.converters;

import com.sun.istack.NotNull;
import com.yahav.coupons.entities.CustomerEntity;
import com.yahav.coupons.models.CustomerRequestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CustomerRequestModelToCustomerEntityConverter implements Converter<CustomerRequestModel, CustomerEntity> {

    private final UserRequestModelToUserEntityConverter userRequestModelToUserEntityConverter;

    @Autowired
    public CustomerRequestModelToCustomerEntityConverter(@Lazy UserRequestModelToUserEntityConverter userRequestModelToUserEntityConverter) {
        this.userRequestModelToUserEntityConverter = userRequestModelToUserEntityConverter;
    }

    @Override
    public CustomerEntity convert(@NotNull CustomerRequestModel customerRequestModel) {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(customerRequestModel.getUser().getId());
        customerEntity.setUser(userRequestModelToUserEntityConverter.convert(customerRequestModel.getUser()));
        customerEntity.setAddress(customerRequestModel.getAddress());
        if (customerRequestModel.getAmountOfChildren() != null) {
            customerEntity.setAmountOfChildren(customerRequestModel.getAmountOfChildren());
        }
        customerEntity.setBirthDate(customerRequestModel.getBirthDate());
        customerEntity.setGender(customerRequestModel.getGender());
        customerEntity.setMaritalStatus(customerRequestModel.getMaritalStatus());
        return customerEntity;
    }
}

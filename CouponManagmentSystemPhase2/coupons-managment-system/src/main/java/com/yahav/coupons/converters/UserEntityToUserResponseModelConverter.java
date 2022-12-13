package com.yahav.coupons.converters;

import com.sun.istack.NotNull;
import com.yahav.coupons.entities.UserEntity;
import com.yahav.coupons.models.UserResponseModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserEntityToUserResponseModelConverter implements Converter<UserEntity, UserResponseModel> {


    @Override
    public UserResponseModel convert(@NotNull UserEntity userEntity) {
        UserResponseModel userResponseModel = new UserResponseModel();
        userResponseModel.setId(userEntity.getId());
        userResponseModel.setUsername(userEntity.getUsername());
        if (userEntity.getCompany() != null) {
            userResponseModel.setCompanyId(userEntity.getCompany().getId());
        }
        userResponseModel.setFirstName(userEntity.getFirstName());
        userResponseModel.setLastName(userEntity.getLastName());
        userResponseModel.setPhone(userEntity.getPhone());
        userResponseModel.setJoinDate(userEntity.getJoinDate());
        userResponseModel.setRole(userEntity.getRole());
        userResponseModel.setToken(userEntity.getToken());
        return userResponseModel;
    }
}

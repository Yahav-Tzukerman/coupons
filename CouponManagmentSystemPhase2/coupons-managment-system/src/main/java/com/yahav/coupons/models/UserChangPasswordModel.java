package com.yahav.coupons.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserChangPasswordModel {
    private String oldPassword;
    private String newPassword;
}

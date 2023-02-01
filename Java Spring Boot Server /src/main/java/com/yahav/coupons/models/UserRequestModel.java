package com.yahav.coupons.models;

import com.yahav.coupons.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestModel {
    private long id;
    private String username;
    private String password;
    private Role role;
    private Long companyId;
    private String firstName;
    private String lastName;
    private String phone;
    private LocalDate joinDate;
}

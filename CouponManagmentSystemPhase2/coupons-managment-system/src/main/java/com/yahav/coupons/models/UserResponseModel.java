package com.yahav.coupons.models;

import com.yahav.coupons.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseModel {
    private long id;
    private String username;
    private Long companyId;
    private String firstName;
    private String lastName;
    private String phone;
    private LocalDate joinDate;
    private Role role;
    private transient String token;

    public UserResponseModel(long id, String username, Long companyId, String firstName, String lastName, String phone, LocalDate joinDate, Role role) {
        this.id = id;
        this.username = username;
        this.companyId = companyId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.joinDate = joinDate;
        this.role = role;
    }
}

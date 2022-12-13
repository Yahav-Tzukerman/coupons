package com.yahav.coupons.models;

import com.yahav.coupons.enums.Gender;
import com.yahav.coupons.enums.MaritalStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequestModel {
    private UserRequestModel user;
    private String address;
    private Integer amountOfChildren;
    private LocalDate birthDate;
    private Gender gender;
    private MaritalStatus maritalStatus;
}

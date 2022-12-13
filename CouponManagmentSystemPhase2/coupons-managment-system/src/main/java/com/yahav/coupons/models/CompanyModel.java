package com.yahav.coupons.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyModel {
    private long id;
    private String name;
    private String phone;
    private String address;
    private LocalDate foundationDate;
    private String logo;
}

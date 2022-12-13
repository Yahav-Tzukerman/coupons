package com.yahav.coupons.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "companies")
public class CompanyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "foundation_date")
    private LocalDate foundationDate;

    @Column(name = "logo")
    private String logo;

    @OneToMany(mappedBy = "company", cascade = CascadeType.REMOVE)
    private List<CouponEntity> coupons;

    @OneToMany(mappedBy = "company", cascade = CascadeType.REMOVE)
    private List<UserEntity> users;

}

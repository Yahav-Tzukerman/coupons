package com.yahav.coupons.entities;

import com.yahav.coupons.enums.Gender;
import com.yahav.coupons.enums.MaritalStatus;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "customers")
public class CustomerEntity {

    @Id
    private long id;

    @MapsId
    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE, CascadeType.MERGE})
    @JoinColumn(name = "id", referencedColumnName = "id")
    private UserEntity user;

    @Column(name = "address")
    private String address;

    @Column(name = "amount_of_children")
    private int amountOfChildren;

    @Column(name = "birthdate")
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "martial_status")
    private MaritalStatus maritalStatus;
}

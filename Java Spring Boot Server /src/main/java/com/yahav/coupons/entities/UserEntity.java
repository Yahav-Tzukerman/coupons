package com.yahav.coupons.entities;

import com.yahav.coupons.enums.Role;
import com.yahav.coupons.enums.Status;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "phone")
    private String phone;

    @Column(name = "join_date", nullable = false)
    private LocalDate joinDate;

    @Column(name = "amount_of_failed_logins", nullable = false)
    private int amountOfFailedLogins;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Column(name = "suspension_timestamp")
    private LocalDateTime suspensionTimeStamp;

    @ManyToOne
    private CompanyEntity company;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<PurchaseEntity> purchases;

    @Transient
    private String token;
}

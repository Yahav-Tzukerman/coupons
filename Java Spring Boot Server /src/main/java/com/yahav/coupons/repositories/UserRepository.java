package com.yahav.coupons.repositories;

import com.yahav.coupons.entities.CompanyEntity;
import com.yahav.coupons.entities.UserEntity;
import com.yahav.coupons.enums.Role;
import com.yahav.coupons.models.UserResponseModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    List<UserEntity> findByUsername(String username);

    List<UserEntity> findByCompany(CompanyEntity company);

    Page<UserEntity> findByCompany(CompanyEntity company, Pageable pageable);

    Page<UserEntity> findByRole(Role role, Pageable pageable);

    boolean existsByUsername(String username);

    @Query("SELECT new com.yahav.coupons.models.UserResponseModel(u.id, u.username, c.id, u.firstName, u.lastName, u.phone, u.joinDate, u.role) " +
            "FROM UserEntity u LEFT OUTER JOIN u.company c WHERE u.id = :id")
    UserResponseModel getUser(@Param("id") long id);
}
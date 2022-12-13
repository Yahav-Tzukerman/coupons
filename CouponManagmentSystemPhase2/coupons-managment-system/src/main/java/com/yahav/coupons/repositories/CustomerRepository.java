package com.yahav.coupons.repositories;

import com.yahav.coupons.entities.CustomerEntity;
import com.yahav.coupons.models.CustomerResponseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    @Query("SELECT new com.yahav.coupons.models.CustomerResponseModel(c.id, c.address, c.amountOfChildren, c.birthDate, c.gender, c.maritalStatus) " +
            "FROM CustomerEntity c WHERE c.id= :id")
    CustomerResponseModel getCustomer(@Param("id") long id);
}

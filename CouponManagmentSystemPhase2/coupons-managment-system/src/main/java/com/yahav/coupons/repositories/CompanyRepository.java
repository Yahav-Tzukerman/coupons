package com.yahav.coupons.repositories;

import com.yahav.coupons.entities.CompanyEntity;
import com.yahav.coupons.models.CompanyListModel;
import com.yahav.coupons.models.CompanyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {

    @Query("SELECT new com.yahav.coupons.models.CompanyModel(c.id, c.name, c.phone, c.address, c.foundationDate, c.logo) " +
            "FROM CompanyEntity c WHERE id = :id")
    CompanyModel getCompanyById(@Param("id") long id);

    boolean existsByName(String name);

    Optional<CompanyEntity> findByName(String name);

    @Query("SELECT new com.yahav.coupons.models.CompanyListModel(c.id, c.name) " +
            "FROM CompanyEntity c")
    List<CompanyListModel> companiesList();
}
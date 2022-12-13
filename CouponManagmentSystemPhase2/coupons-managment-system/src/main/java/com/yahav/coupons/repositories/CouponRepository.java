package com.yahav.coupons.repositories;

import com.yahav.coupons.entities.CategoryEntity;
import com.yahav.coupons.entities.CompanyEntity;
import com.yahav.coupons.entities.CouponEntity;
import com.yahav.coupons.models.CouponModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface CouponRepository extends JpaRepository<CouponEntity, Long> {

    @Query("SELECT new com.yahav.coupons.models.CouponModel(c.id, c.code, c.title, c.description, c.startDate, " +
            "c.endDate, cat.name, c.amount, c.price, com.id, com.name, c.imageUrl) " +
            "FROM CouponEntity c INNER JOIN c.company com INNER JOIN c.category cat WHERE c.id = :id")
    CouponModel getCoupon(@Param("id") long id);

    @Query("SELECT new com.yahav.coupons.models.CouponModel(c.id, c.code, c.title, c.description, c.startDate, " +
            "c.endDate, cat.name, c.amount, c.price, com.id, com.name, c.imageUrl) " +
            "FROM CouponEntity c INNER JOIN c.company com INNER JOIN c.category cat")
    List<CouponModel> getCoupons();

    @Query("SELECT c FROM CouponEntity c INNER JOIN c.category cat where cat.id = :id")
    List<CouponEntity> getCouponsByCategory(@Param("id") long id);

    boolean existsByCode(String code);

    List<CouponEntity> findByCompany(CompanyEntity companyEntity);

    Page<CouponEntity> findByCompany(CompanyEntity companyEntity, Pageable pageable);

    @Query("SELECT new com.yahav.coupons.models.CouponModel(c.id, c.code, c.title, c.description, c.startDate, " +
            "c.endDate, cat.name, c.amount, c.price, com.id, com.name, c.imageUrl) " +
            "FROM CouponEntity c INNER JOIN c.company com INNER JOIN c.category cat WHERE c.endDate <= current_date()")
    List<CouponModel> getExpiredCoupons();

    Page<CouponEntity> findByCategory(CategoryEntity categoryEntity, Pageable pageable);

    @Query("SELECT c FROM CouponEntity c WHERE c.price <= :maxPrice")
    Page<CouponEntity> findByMaxPrice(@Param("maxPrice") float maxPrice, Pageable withPage);

    @Query("SELECT c From CouponEntity c WHERE c.title LIKE %:search%")
    Page<CouponEntity> searchByTitle(@Param("search") String search, Pageable pageable);
}

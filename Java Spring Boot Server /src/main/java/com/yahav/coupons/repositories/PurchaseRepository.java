package com.yahav.coupons.repositories;

import com.yahav.coupons.entities.CompanyEntity;
import com.yahav.coupons.entities.CouponEntity;
import com.yahav.coupons.entities.PurchaseEntity;
import com.yahav.coupons.entities.UserEntity;
import com.yahav.coupons.models.PurchaseResponseModel;
import com.yahav.coupons.models.Revenue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<PurchaseEntity, Long> {

    @Query("SELECT p FROM PurchaseEntity p INNER JOIN p.coupon c WHERE c.company = :company")
    Page<PurchaseEntity> findByCompany(@Param("company") CompanyEntity companyEntity, Pageable pageable);

    @Query("SELECT new com.yahav.coupons.models.PurchaseResponseModel(p.id, c.id, u.id, p.purchaseTimestamp, p.amount, p.totalPrice) " +
            "FROM PurchaseEntity p INNER JOIN p.coupon c INNER JOIN p.user u")
    PurchaseResponseModel getPurchase(@Param("id") long id);

    @Query("SELECT sum(totalPrice) FROM PurchaseEntity")
    float findTotalPurchases();

    @Query("SELECT new com.yahav.coupons.models.Revenue(MONTH(p.purchaseTimestamp), SUM(p.totalPrice)) " +
            "FROM PurchaseEntity p " +
            "WHERE p.purchaseTimestamp > now() - 31557600000 " +
            "GROUP BY MONTH(p.purchaseTimestamp)")
    List<Revenue> revenue();

    List<PurchaseEntity> findByCoupon(CouponEntity coupon);

    List<PurchaseEntity> findByUser(UserEntity user);

    Page<PurchaseEntity> findByCoupon(CouponEntity coupon, Pageable pageable);

    Page<PurchaseEntity> findByUser(UserEntity user, Pageable pageable);
}

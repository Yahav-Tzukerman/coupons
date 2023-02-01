package com.yahav.coupons.services;

import com.yahav.coupons.converters.PurchaseEntityToPurchaseResponseModelConverter;
import com.yahav.coupons.converters.PurchaseRequestModelToPurchaseEntityConverter;
import com.yahav.coupons.entities.CompanyEntity;
import com.yahav.coupons.entities.CouponEntity;
import com.yahav.coupons.entities.PurchaseEntity;
import com.yahav.coupons.entities.UserEntity;
import com.yahav.coupons.enums.ErrorType;
import com.yahav.coupons.exceptions.ServerException;
import com.yahav.coupons.models.*;
import com.yahav.coupons.repositories.PurchaseRepository;
import com.yahav.coupons.security.jwt.JwtUserValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final UserService userService;
    private final CouponService couponService;
    private final CompanyService companyService;
    private final JwtUserValidator jwtUserValidator;
    private final PurchaseEntityToPurchaseResponseModelConverter purchaseEntityToPurchaseResponseModelConverter;
    private final PurchaseRequestModelToPurchaseEntityConverter purchaseRequestModelToPurchaseEntityConverter;

    @Autowired
    public PurchaseService(PurchaseRepository purchaseRepository, @Lazy UserService userService, @Lazy CouponService couponService
            , @Lazy CompanyService companyService, @Lazy JwtUserValidator jwtUserValidator, @Lazy PurchaseEntityToPurchaseResponseModelConverter purchaseEntityToPurchaseResponseModelConverter
            , @Lazy PurchaseRequestModelToPurchaseEntityConverter purchaseRequestModelToPurchaseEntityConverter) {
        this.purchaseRepository = purchaseRepository;
        this.userService = userService;
        this.couponService = couponService;
        this.companyService = companyService;
        this.jwtUserValidator = jwtUserValidator;
        this.purchaseEntityToPurchaseResponseModelConverter = purchaseEntityToPurchaseResponseModelConverter;
        this.purchaseRequestModelToPurchaseEntityConverter = purchaseRequestModelToPurchaseEntityConverter;
    }

    public void initPurchase(PurchaseEntity purchaseEntity) {
        purchaseEntity.setPurchaseTimestamp(LocalDateTime.now());
        float couponPrice = purchaseEntity.getCoupon().getPrice();
        int amount = purchaseEntity.getAmount();
        purchaseEntity.setTotalPrice(couponPrice * amount);
    }

    // Validated Customer
    @Transactional
    public PurchaseResponseModel savePurchase(PurchaseRequestModel purchaseRequestModel, HttpServletRequest request) throws ServerException {
        validatePurchase(purchaseRequestModel, request);
        PurchaseEntity purchaseEntity = purchaseRequestModelToPurchaseEntityConverter.convert(purchaseRequestModel);
        if (purchaseEntity == null) {
            throw new ServerException(ErrorType.GENERAL_ERROR);
        }
        initPurchase(purchaseEntity);
        purchaseEntity = purchaseRepository.save(purchaseEntity);
        couponService.decreaseAmount(purchaseEntity.getCoupon().getId(), purchaseEntity.getAmount(), request);
        return purchaseEntityToPurchaseResponseModelConverter.convert(purchaseEntity);
    }

    // only Admin
    public Page<PurchaseResponseModel> getAllPurchases(int pageSize, int pageNumber) {
        Page<PurchaseEntity> purchaseEntityPage = purchaseRepository.findAll(Pageable.ofSize(pageSize).withPage(pageNumber));
        return purchaseEntityPage.map(purchaseEntityToPurchaseResponseModelConverter::convert);
    }

    // only Admin
    public float getAllPurchasesAmount() {
        return purchaseRepository.findTotalPurchases();
    }

    // Admin or validated company user
    public Page<PurchaseResponseModel> getAllPurchasesByCouponId(long couponId, int pageSize, int pageNumber, HttpServletRequest request) throws ServerException {
        jwtUserValidator.validateCompanyCouponFromJWT(couponId, request);
        CouponEntity coupon = couponService.getCouponEntity(couponId);
        Page<PurchaseEntity> purchaseEntityPage = purchaseRepository.findByCoupon(coupon, Pageable.ofSize(pageSize).withPage(pageNumber));
        return purchaseEntityPage.map(purchaseEntityToPurchaseResponseModelConverter::convert);
    }

    //Admin or validated company user
    public Page<PurchaseResponseModel> getAllPurchasesByCompanyId(long companyId, int pageSize, int pageNumber, HttpServletRequest request) throws ServerException {
        CompanyEntity company = companyService.getCompanyEntity(companyId);
        jwtUserValidator.validateCompanyUserFromJWT(companyId, request);
        Page<PurchaseEntity> purchaseEntityPage = purchaseRepository.findByCompany(company, Pageable.ofSize(pageSize).withPage(pageNumber));
        return purchaseEntityPage.map(purchaseEntityToPurchaseResponseModelConverter::convert);
    }

    // Admin or Validated Customer
    public Page<PurchaseResponseModel> getAllPurchasesByUserId(long userId, int pageSize, int pageNumber, HttpServletRequest request) throws ServerException {
        jwtUserValidator.validateUserIdFromJWT(userId, request);
        UserEntity user = userService.getUserEntity(userId);
        Page<PurchaseEntity> purchaseEntityPage = purchaseRepository.findByUser(user, Pageable.ofSize(pageSize).withPage(pageNumber));
        return purchaseEntityPage.map(purchaseEntityToPurchaseResponseModelConverter::convert);
    }

    // Admin or Validated Customer
    public PurchaseResponseModel getPurchase(long id, HttpServletRequest request) throws ServerException {
        jwtUserValidator.validateUserIdFromJWT(id, request);
        return purchaseRepository.getPurchase(id);
    }

    // Only Admin
    @Transactional
    public void deletePurchase(long id, HttpServletRequest request) throws ServerException {
        PurchaseResponseModel purchase = purchaseRepository.getPurchase(id);
        if (purchase == null) {
            return;
        }
        CouponModel coupon = couponService.getCoupon(purchase.getCouponId());
        purchaseRepository.deleteById(id);

        coupon.setAmount(coupon.getAmount() + purchase.getAmount());
        couponService.updateCoupon(coupon, request);
    }

    //Only Admin
    public List<Revenue> getRevenue() {
        Map<Integer, Revenue> revenueMap = purchaseRepository.revenue()
                .stream()
                .collect(Collectors.toMap(Revenue::getMonth, Function.identity()));
        for (int i = 1; i <= 12; i++) {
            revenueMap.putIfAbsent(i, new Revenue(i, 0));
        }
        return new ArrayList<>(revenueMap.values());
    }

    // Internal Server Use
    public List<PurchaseEntity> getPurchaseEntitiesByCouponId(long id) throws ServerException {
        CouponEntity coupon = couponService.getCouponEntity(id);
        return purchaseRepository.findByCoupon(coupon);
    }

    // Internal Server Use
    public List<PurchaseEntity> getPurchaseEntitiesByUserId(long id) throws ServerException {
        UserEntity user = userService.getUserEntity(id);
        return purchaseRepository.findByUser(user);
    }

    private void validatePurchase(PurchaseRequestModel purchaseRequestModel, HttpServletRequest request) throws ServerException {
        jwtUserValidator.validateUserIdFromJWT(purchaseRequestModel.getUserId(), request);
        UserResponseModel user = userService.getUser(purchaseRequestModel.getUserId());
        CouponModel couponModel = couponService.getCoupon(purchaseRequestModel.getCouponId());
        if (user == null) {
            throw new ServerException(ErrorType.USER_DOES_NOT_EXIST);
        }
        if (couponModel == null) {
            throw new ServerException(ErrorType.COUPON_DOES_NOT_EXIST);
        }
        if (purchaseRequestModel.getAmount() < 1) {
            throw new ServerException(ErrorType.INVALID_AMOUNT);
        }
        if (couponModel.getAmount() < purchaseRequestModel.getAmount()) {
            throw new ServerException(ErrorType.INVALID_AMOUNT);
        }
        if (couponModel.getEndDate().isBefore(LocalDate.now())) {
            throw new ServerException(ErrorType.INVALID_DATE, "Coupon is expired");
        }
    }
}

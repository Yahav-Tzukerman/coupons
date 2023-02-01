package com.yahav.coupons.security.jwt;

import com.yahav.coupons.entities.CouponEntity;
import com.yahav.coupons.entities.UserEntity;
import com.yahav.coupons.enums.ErrorType;
import com.yahav.coupons.enums.Role;
import com.yahav.coupons.exceptions.ServerException;
import com.yahav.coupons.models.CouponModel;
import com.yahav.coupons.models.UserResponseModel;
import com.yahav.coupons.security.UserPrincipal;
import com.yahav.coupons.services.CouponService;
import com.yahav.coupons.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Service
public class JwtUserValidator {

    private final JwtProvider jwtProvider;
    private final UserService userService;
    private final CouponService couponService;

    @Autowired
    public JwtUserValidator(JwtProvider jwtProvider,
                            @Lazy UserService userService
            , @Lazy CouponService couponService) {
        this.jwtProvider = jwtProvider;
        this.userService = userService;
        this.couponService = couponService;
    }

    public void validateCompanyIdFromJWT(long companyId, HttpServletRequest request) throws ServerException {
        UserPrincipal userPrincipal = jwtProvider.getUserPrincipal(request);
        Role userRole = userPrincipal.getUserRole();
        if (userRole == Role.COMPANY) {
            UserResponseModel user = userService.getUserRequest(userPrincipal.getId(), request);
            if (companyId != user.getCompanyId()) {
                throw new ServerException(ErrorType.NO_PERMISSION_GRANTED);
            }
        }
    }

    public void validateIdFromJWT(long userId, HttpServletRequest request) throws ServerException {
        UserPrincipal userPrincipal = jwtProvider.getUserPrincipal(request);
        Role userRole = userPrincipal.getUserRole();
        if (userRole == Role.COMPANY || userRole == Role.CUSTOMER) {
            if (userPrincipal.getId() != userId) {
                throw new ServerException(ErrorType.NO_PERMISSION_GRANTED);
            }
        }
    }

    public void validateCompanyUser(CouponModel coupon, HttpServletRequest request) throws ServerException {
        UserPrincipal userPrincipal = jwtProvider.getUserPrincipal(request);
        Role userRole = userPrincipal.getUserRole();
        UserResponseModel user = userService.getUser(userPrincipal.getId());
        if (userRole == Role.COMPANY) {
            if (user.getCompanyId() != coupon.getCompanyId()) {
                throw new ServerException(ErrorType.NO_PERMISSION_GRANTED);
            }
        }
    }

    public void validateCompanyUserFromJWT(long companyId, HttpServletRequest request) throws ServerException {
        UserPrincipal userPrincipal = jwtProvider.getUserPrincipal(request);
        Role userRole = userPrincipal.getUserRole();
        if (userRole == Role.COMPANY) {
            UserEntity user = userService.getUserEntity(userPrincipal.getId());
            if (user.getCompany().getId() != companyId) {
                throw new ServerException(ErrorType.NO_PERMISSION_GRANTED);
            }
        }
        if (userRole == Role.CUSTOMER) {
            throw new ServerException(ErrorType.NO_PERMISSION_GRANTED);
        }
    }

    public void validateCompanyCouponFromJWT(long couponId, HttpServletRequest request) throws ServerException {
        UserPrincipal userPrincipal = jwtProvider.getUserPrincipal(request);
        Role userRole = userPrincipal.getUserRole();
        if (userRole == Role.COMPANY) {
            UserEntity user = userService.getUserEntity(userPrincipal.getId());
            CouponEntity coupon = couponService.getCouponEntity(couponId);
            if (user.getCompany().getId() != coupon.getCompany().getId()) {
                throw new ServerException(ErrorType.NO_PERMISSION_GRANTED);
            }
        }
        if (userRole == Role.CUSTOMER) {
            throw new ServerException(ErrorType.NO_PERMISSION_GRANTED);
        }
    }

    public void validateUserIdFromJWT(long userId, HttpServletRequest request) throws ServerException {
        UserPrincipal userPrincipal = jwtProvider.getUserPrincipal(request);
        Role userRole = userPrincipal.getUserRole();
        if (userRole == Role.CUSTOMER) {
            if (userPrincipal.getId() != userId) {
                log.error("User: " + userPrincipal.getId() + " tried to identify as: " + userId + " Hacking potential!!!");
                throw new ServerException(ErrorType.NO_PERMISSION_GRANTED);
            }
        }
        if (userRole == Role.COMPANY) {
            throw new ServerException(ErrorType.NO_PERMISSION_GRANTED);
        }
    }
}

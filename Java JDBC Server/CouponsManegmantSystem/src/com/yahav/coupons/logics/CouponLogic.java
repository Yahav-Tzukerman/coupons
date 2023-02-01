package com.yahav.coupons.logics;

import com.yahav.coupons.beans.Company;
import com.yahav.coupons.beans.Coupon;
import com.yahav.coupons.dal.CouponsDal;
import com.yahav.coupons.enums.Category;
import com.yahav.coupons.enums.ErrorType;
import com.yahav.coupons.exceptions.ServerException;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CouponLogic {
    private static final CouponsDal couponsDal = new CouponsDal();
    private static final CompanyLogic companyLogic = new CompanyLogic();
    private static final PurchaseLogic purchaseLogic = new PurchaseLogic();

    public CouponLogic() {
    }

    public int updateCoupon(Coupon coupon) throws ServerException {
        try {
            coupon.setId(couponsDal.getId(coupon.getCode()));
            validateCoupon(coupon);
            return couponsDal.updateCoupon(coupon);
        } catch (ServerException e) {
            throw new ServerException(e, ErrorType.UPDATE_ERROR, "Failed to update coupon: " + coupon.getCode());

        }
    }

    public Coupon getCoupon(String couponCode) throws ServerException {
        int couponId = couponsDal.getId(couponCode);
        return getCoupon(couponId);
    }

    public Coupon getCoupon(int couponId) throws ServerException {
        try {
            return couponsDal.getCoupon(couponId);
        } catch (ServerException e) {
            throw new ServerException(e, ErrorType.GET_OBJECT_ERROR, "Failed to get coupon");
        }
    }

    public List<Coupon> getCouponsByCategory(Category category) throws ServerException {
        try {
            return couponsDal.getCouponsByCategory(category);
        } catch (ServerException e) {
            throw new ServerException(e, ErrorType.GET_OBJECT_ERROR, "Failed to get coupons by category: " + category);
        }
    }

    public List<Coupon> getCoupons() throws ServerException {
        try {
            return couponsDal.getCoupons();
        } catch (ServerException e) {
            throw new ServerException(e, ErrorType.GET_OBJECT_ERROR, "Failed to get all coupons");
        }
    }

    public void clearContent() throws ServerException {
        try {
            couponsDal.clearContent();
        } catch (ServerException e) {
            throw new ServerException(e, ErrorType.GENERAL_ERROR, "Failed to clear coupons content");
        }
    }

    public void deleteCompanyCoupons(String companyName) throws ServerException {
        try {
            Company company = companyLogic.getCompany(companyName);
            if (couponsDal.isCompanyCouponsExist(company.getId())) {
                couponsDal.deleteCompanyCoupons(company.getId());
                System.out.println(companyName + " coupons was deleted successfully");
            }
        } catch (ServerException e) {
            throw new ServerException(e, ErrorType.DELETION_ERROR, "Failed to delete coupons for company : " + companyName);
        }
    }

    public int deleteCoupon(String couponCode) throws ServerException {
        int couponId = couponsDal.getId(couponCode);
        if (couponId == -1) {
            throw new ServerException(ErrorType.COUPON_DOES_NOT_EXIST, "Coupon: " + couponCode);
        }
        return deleteCoupon(couponId);
    }

    public int deleteCoupon(int couponId) throws ServerException {
        String couponCode = "";
        try {
            Coupon coupon = getCoupon(couponId);
            if(coupon == null){
                throw new ServerException(ErrorType.COUPON_DOES_NOT_EXIST);
            }
            couponCode = coupon.getCode();
            purchaseLogic.deleteCouponPurchases(couponCode);
            return couponsDal.deleteCoupon(couponId);
        } catch (ServerException e) {
            throw new ServerException(e, ErrorType.DELETION_ERROR, "Failed to delete coupon: " + couponCode);
        }
    }

    public int addCoupon(Coupon coupon) throws ServerException {
        try {
            if (couponsDal.isCouponExist(coupon.getCode())) {
                throw new ServerException(ErrorType.COUPON_ALREADY_EXIST, "Coupon: " + coupon.getCode());
            }
            validateCoupon(coupon);
            return couponsDal.addCoupon(coupon);
        } catch (ServerException e) {
            throw new ServerException(e, ErrorType.CREATION_ERROR, "Failed to add " + coupon.getCode());
        }
    }

    private void validateCoupon(Coupon coupon) throws ServerException {
        if (!codeValidation(coupon.getCode())) {
            throw new ServerException(ErrorType.INVALID_COUPON_CODE);
        }
        if (coupon.getTitle().length() < 2 && coupon.getTitle().length() > 30) {
            throw new ServerException(ErrorType.INVALID_FILED, "title must be between 2-30 characters");
        }
        if (coupon.getDescription().length() < 2 && coupon.getDescription().length() > 250) {
            throw new ServerException(ErrorType.INVALID_FILED, "Description must between 2-250 characters");
        }
        Date today = Date.valueOf(LocalDate.now());
        if (coupon.getStartDate().before(today)) {
            throw new ServerException(ErrorType.INVALID_DATE, "Coupon start date cant be before today");
        }
        if (coupon.getEndDate().before(coupon.getStartDate())) {
            throw new ServerException(ErrorType.INVALID_DATE, "Coupon end date cant be before start date");
        }
        if (coupon.getEndDate().toLocalDate().isAfter(today.toLocalDate().plusYears(1))) {
            throw new ServerException(ErrorType.INVALID_DATE, "Coupon end date cant be more then a year from today");
        }
        if (coupon.getAmount() < 1) {
            throw new ServerException(ErrorType.INVALID_FILED, "Coupon amount must be positive and not 0");
        }
        if (coupon.getPrice() <= 0) {
            throw new ServerException(ErrorType.INVALID_FILED, "Coupon price must be positive and not 0");
        }
        if (!imageFileValidation(coupon.getImage())) {
            throw new ServerException(ErrorType.INVALID_IMAGE);
        }
        companyLogic.getCompany(coupon.getCompanyName());
    }

    private boolean codeValidation(String code) {
        String regex = "^[A-Z0-9]{2,16}+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(code);
        return matcher.matches();
    }

    private boolean imageFileValidation(String image) {
        String regex = "([^\\s]+(\\.(?i)(jpe?g|png|gif|bmp))$)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(image);
        return matcher.matches();
    }

    public int addInvalidCouponForJobTest(Coupon coupon) throws ServerException {
        if (couponsDal.isCouponExist(coupon.getCode())) {
            throw new ServerException(ErrorType.COUPON_ALREADY_EXIST, "Coupon: " + coupon.getCode());
        }
        validateCouponWithoutStartDateValidationForJobTest(coupon);
        return couponsDal.addCoupon(coupon);
    }

    private void validateCouponWithoutStartDateValidationForJobTest(Coupon coupon) throws ServerException {
        if (!codeValidation(coupon.getCode())) {
            throw new ServerException(ErrorType.INVALID_COUPON_CODE);
        }
        if (coupon.getTitle().length() < 2 && coupon.getTitle().length() > 30) {
            throw new ServerException(ErrorType.INVALID_FILED, "title must be between 2-30 characters");
        }
        if (coupon.getDescription().length() < 2 && coupon.getDescription().length() > 500) {
            throw new ServerException(ErrorType.INVALID_FILED, "Description must between 2-500 characters");
        }
        Date today = Date.valueOf(LocalDate.now());
        if (coupon.getEndDate().toLocalDate().isAfter(today.toLocalDate().plusYears(1))) {
            throw new ServerException(ErrorType.INVALID_DATE, "Coupon end date cant be more then a year from today");
        }
        if (coupon.getAmount() < 1) {
            throw new ServerException(ErrorType.INVALID_FILED, "Amount must be positive and not 0");
        }
        if (coupon.getPrice() <= 0) {
            throw new ServerException(ErrorType.INVALID_FILED, "Price must be positive and not 0");
        }
        if (!imageFileValidation(coupon.getImage())) {
            throw new ServerException(ErrorType.INVALID_IMAGE);
        }
        companyLogic.getCompany(coupon.getCompanyName());
    }
}

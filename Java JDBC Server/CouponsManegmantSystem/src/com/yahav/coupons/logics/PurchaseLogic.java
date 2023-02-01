package com.yahav.coupons.logics;

import com.yahav.coupons.beans.Company;
import com.yahav.coupons.beans.Coupon;
import com.yahav.coupons.beans.Purchase;
import com.yahav.coupons.beans.User;
import com.yahav.coupons.dal.PurchaseDal;
import com.yahav.coupons.enums.ErrorType;
import com.yahav.coupons.enums.UserType;
import com.yahav.coupons.exceptions.ServerException;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class PurchaseLogic {
    private static final PurchaseDal purchaseDal = new PurchaseDal();
    private static final UserLogic userLogic = new UserLogic();
    private static final CouponLogic couponLogic = new CouponLogic();
    private static final CompanyLogic companyLogic = new CompanyLogic();

    public PurchaseLogic() {
    }

    public int updatePurchase(Purchase purchase) throws ServerException {
        try {
            if (!purchaseDal.isPurchaseExist(purchase.getPurchaseId())) {
                throw new ServerException(ErrorType.PURCHASE_DOES_NOT_EXIST);
            }
            validatePurchase(purchase);
            return purchaseDal.updatePurchase(purchase);
        } catch (ServerException e) {
            throw new ServerException(e, ErrorType.UPDATE_ERROR, "Failed to update purchase for: " + purchase.getUsername() + " ," + purchase.getCouponCode());
        }
    }

    public Purchase getPurchase(String username, String couponCode) throws ServerException {
        int purchaseId = purchaseDal.getId(username, couponCode);
        return getPurchase(purchaseId);
    }

    public Purchase getPurchase(int purchaseId) throws ServerException {
        try {
            return purchaseDal.getPurchase(purchaseId);
        } catch (ServerException e) {
            throw new ServerException(e, ErrorType.GET_OBJECT_ERROR, "Failed to get purchase");
        }
    }

    public List<Purchase> getUserPurchasesByMaxPrice(String username, float maxPrice) throws ServerException {
        try {
            return purchaseDal.getUserPurchasesByMaxPrice(username, maxPrice);
        } catch (ServerException e) {
            throw new ServerException(e, ErrorType.GET_OBJECT_ERROR, "Failed to get all purchases by max price");
        }
    }

    public List<Purchase> getCompanyPurchases(String companyName) throws ServerException {
        try {
            Company company = companyLogic.getCompany(companyName);
            return purchaseDal.getUserPurchases(company.getId());
        } catch (ServerException e) {
            throw new ServerException(e, ErrorType.GET_OBJECT_ERROR, "Failed to get all purchases for : " + companyName);
        }
    }

    public List<Purchase> getCouponPurchases(String couponCode) throws ServerException {
        try {
            Coupon coupon = couponLogic.getCoupon(couponCode);
            return purchaseDal.getUserPurchases(coupon.getId());
        } catch (ServerException e) {
            throw new ServerException(e, ErrorType.GET_OBJECT_ERROR, "Failed to get all purchases for : " + couponCode);
        }
    }

    public List<Purchase> getUserPurchases(String username) throws ServerException {
        try {
            User user = userLogic.getUser(username);
            return purchaseDal.getUserPurchases(user.getId());
        } catch (ServerException e) {
            throw new ServerException(e, ErrorType.GET_OBJECT_ERROR, "Failed to get all purchases for : " + username);
        }
    }

    public List<Purchase> getPurchases() throws ServerException {
        try {
            return purchaseDal.getPurchases();
        } catch (ServerException e) {
            throw new ServerException(e, ErrorType.GET_OBJECT_ERROR, "Failed to get all purchases");
        }
    }

    public void clearContent() throws ServerException {
        try {
            purchaseDal.clearContent();
        } catch (ServerException e) {
            throw new ServerException(e, ErrorType.GENERAL_ERROR, "Failed to clear purchases content");
        }
    }

    public void deleteCouponPurchases(String couponCode) throws ServerException {
        try {
            Coupon coupon = couponLogic.getCoupon(couponCode);
            if (coupon != null) {
                List<Purchase> couponPurchases = purchaseDal.getCouponPurchases(coupon.getId());
                for (Purchase purchase : couponPurchases) {
                    purchaseDal.deletePurchase(purchase.getPurchaseId());
                }
                System.out.println(couponCode + " purchases was deleted successfully");
            }
        } catch (ServerException e) {
            throw new ServerException(e, ErrorType.DELETION_ERROR, "Failed to delete " + couponCode + " purchases");
        }
    }

    public void deleteCompanyPurchases(String companyName) throws ServerException {
        try {
            Company company = companyLogic.getCompany(companyName);
            if (company != null) {
                List<Purchase> companyPurchases = purchaseDal.getCompanyPurchases(company.getId());
                for (Purchase purchase : companyPurchases) {
                    purchaseDal.deletePurchase(purchase.getPurchaseId());
                }
                System.out.println(companyName + " purchases was deleted successfully");
            }
        } catch (ServerException e) {
            throw new ServerException(e, ErrorType.DELETION_ERROR, "Failed to delete " + companyName + " purchases");
        }
    }

    public void deleteUserPurchases(String username) throws ServerException {
        try {
            User user = userLogic.getUser(username);
            if (user != null) {
                List<Purchase> userPurchases = purchaseDal.getUserPurchases(user.getId());
                for (Purchase purchase : userPurchases) {
                    purchaseDal.deletePurchase(purchase.getPurchaseId());
                }
                System.out.println(username + " purchases was deleted successfully");
            }
        } catch (ServerException e) {
            throw new ServerException(e, ErrorType.DELETION_ERROR, "Failed to delete " + username + " purchases");
        }
    }

    public int deletePurchases(String username, String couponCode) throws ServerException {
        int purchaseId = purchaseDal.getId(username, couponCode);
        int result = 0;
        while (purchaseId != -1) {
            result = deletePurchase(purchaseId);
            purchaseId = purchaseDal.getId(username, couponCode);
        }
        return result;
    }

    public int deletePurchase(int purchaseId) throws ServerException {
        try {
            Purchase purchase = getPurchase(purchaseId);
            if (purchase != null) {
                Coupon coupon = couponLogic.getCoupon(purchase.getCouponCode());
                int result = purchaseDal.deletePurchase(purchaseId);
                coupon.setAmount(coupon.getAmount() + purchase.getAmount());
                couponLogic.updateCoupon(coupon);
                return result;
            } else {
                throw new ServerException(ErrorType.PURCHASE_DOES_NOT_EXIST);
            }
        } catch (ServerException e) {
            throw new ServerException(e, ErrorType.DELETION_ERROR, "Failed to delete purchase");
        }
    }

    public int addPurchase(Purchase purchase) throws ServerException {
        try {
            validatePurchase(purchase);
            Coupon coupon = couponLogic.getCoupon(purchase.getCouponCode());
            purchase.setPurchaseDate(Timestamp.valueOf(LocalDateTime.now()));
            int purchaseId = purchaseDal.addPurchase(purchase);
            coupon.setAmount(coupon.getAmount() - purchase.getAmount());
            couponLogic.updateCoupon(coupon);
            return purchaseId;
        } catch (ServerException e) {
            throw new ServerException(e, ErrorType.CREATION_ERROR, "Failed to add purchase: " + purchase.getUsername() + " ," + purchase.getCouponCode());
        }
    }

    private void validatePurchase(Purchase purchase) throws ServerException {
        User user = userLogic.getUser(purchase.getUsername());
        Coupon coupon = couponLogic.getCoupon(purchase.getCouponCode());
        if (user == null) {
            throw new ServerException(ErrorType.USER_DOES_NOT_EXIST);
        }
        if (coupon == null) {
            throw new ServerException(ErrorType.COUPON_DOES_NOT_EXIST);
        }
        if (user.getUserType() != UserType.CUSTOMER) {
            throw new ServerException(ErrorType.INVALID_USER_TYPE);
        }
        if (purchase.getAmount() < 1) {
            throw new ServerException(ErrorType.INVALID_FILED, "Invalid amount");
        }
        if (coupon.getAmount() < purchase.getAmount()) {
            throw new ServerException(ErrorType.INVALID_FILED, "Invalid amount");
        }
        if (coupon.getEndDate().before(Date.valueOf(LocalDate.now()))) {
            throw new ServerException(ErrorType.INVALID_DATE, "Coupon is expired");
        }
    }
}
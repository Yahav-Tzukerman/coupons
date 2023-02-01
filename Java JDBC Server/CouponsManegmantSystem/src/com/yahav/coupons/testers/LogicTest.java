package com.yahav.coupons.testers;

import com.yahav.coupons.beans.*;
import com.yahav.coupons.enums.Category;
import com.yahav.coupons.enums.Gender;
import com.yahav.coupons.enums.MaritalStatus;
import com.yahav.coupons.enums.UserType;
import com.yahav.coupons.exceptions.ServerException;
import com.yahav.coupons.logics.*;

import java.sql.Date;
import java.util.List;


public class LogicTest {

    public static void testLogic() {

    //_________________________________________________________________________________________
    //_____________________________Users Mock__________________________________________________

//        UserLogic userLogic = new UserLogic();
//        User userMock = new User("yahav5@icloud.com", "123456Aa", "yahav", "tzukerman", UserType.CUSTOMER,"0546261850", null);

//        try {
////            boolean login = userLogic.login("yahav@gmail.com" , "123456789");
////            System.out.println("login = " + login);
////            userLogic.addUser(userMock);
////            userLogic.deleteUser(36);
////            List<User> users = userLogic.getAllUsers();
////            for(User user : users){
////                System.out.println(user);
////            }
////            System.out.println(userLogic.getUserById(37));
////            userLogic.updateUser(userMock);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }

    //_________________________________________________________________________________________
    //_____________________________Customers Mock______________________________________________

//        CustomerLogic customerLogic = new CustomerLogic();
//        Customer customerMock = new Customer(userMock , Gender.MALE, MaritalStatus.SINGLE, 0, Date.valueOf("1993-03-02") ,"ISRAEL", "NESHER", "HA-AIRUSIM 24");

//        try {
////            customerLogic.addCustomer(customerMock);
////            customerLogic.deleteCustomer(41);
////            List<Customer> customers = customerLogic.getAllCustomers();
////            for(Customer customer: customers){
////                System.out.println(customer);
////            }
////            System.out.println(customerLogic.getCustomerById(42));
////            customerLogic.updateCustomer(customerMock);
////            System.out.println(customerLogic.isCustomerExist(42));
//        } catch (ServerException e) {
//            System.out.println(e.getMessage());
//        }


    //_________________________________________________________________________________________
    //_____________________________Coupons Mock________________________________________________

//        CouponLogic couponLogic = new CouponLogic();
//        long now = System.currentTimeMillis();
//        Date endDate = Date.valueOf("2023-01-01");
//        Coupon couponMock = new Coupon("PS4",10, Category.ELECTRICITY
//                ,"PS4 50% Discount", "discount 50% on repaired PS4"
//                , new Date(now), endDate, 500, 300f, "4KTV_image");

//        try {
////            System.out.println(couponLogic.isCouponExist("PS5"));
////            couponLogic.addCoupon(couponMock);
////            couponLogic.deleteCouponById(11);
////            couponLogic.deleteAllCompanyCoupons(6);
////            List<Coupon> coupons = couponLogic.getAllCoupons();
////            for(Coupon coupon: coupons){
////                System.out.println(coupon);
////            }
////            System.out.println(couponLogic.getCouponById(11));
////            couponLogic.updateCoupon(couponMock);
//        } catch (ServerException e) {
//            System.out.println(e.getMessage());
//        }


    //_________________________________________________________________________________________
    //_____________________________Companies Mock______________________________________________

//        CompanyLogic companyLogic = new CompanyLogic();
//        Company companyMock = new Company("Sony","1234567800", Date.valueOf("1990-01-01"),"JAPAN","TOKYO","7th Street");

//        try {
////            System.out.println(companyLogic.isCompanyExist(companyMock.getName()));
////            companyLogic.addCompany(companyMock);
////            companyLogic.deleteCompany(8);
////            List<Company> companies = companyLogic.getAllCompanies();
////            for(Company company : companies){
////                System.out.println(company);
////            }
////            System.out.println(companyLogic.getCompanyById(9));
////            companyLogic.updateCompany(companyMock);
//        } catch (ServerException e) {
//            System.out.println(e.getMessage());
//        }


    //_________________________________________________________________________________________
    //_____________________________Purchases Mock______________________________________________

//        PurchaseLogic purchaseLogic = new PurchaseLogic();
//        Purchase purchaseMock = new Purchase("yahav5@icloud.com","PS4", 5);

//        try {
////            System.out.println(purchaseLogic.isPurchasesExist(38));
////            System.out.println(purchaseLogic.isPurchaseExist(38, 13));
////            System.out.println(purchaseLogic.isPurchaseExist("yahav3@icloud.com","PS4"));
////            purchaseLogic.addPurchase(purchaseMock);
////            purchaseLogic.deletePurchases(38);
////            purchaseLogic.deletePurchase(38, 13);
////            List<Purchase> purchases = purchaseLogic.getAllPurchases();
////            for(Purchase purchase : purchases){
////                System.out.println(purchase);
////            }
////            System.out.println(purchaseLogic.getPurchasesByUsername("yahav3@icloud.com"));
////            System.out.println(purchaseLogic.getPurchaseById(38, 13));
////            purchaseLogic.updatePurchase(purchaseMock);
//
//        } catch (ServerException e) {
//            System.out.println(e.getMessage());
//        }

    }
}

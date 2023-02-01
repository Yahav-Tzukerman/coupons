package com.yahav.coupons.testers;

import com.yahav.coupons.beans.*;
import com.yahav.coupons.dal.*;

import java.util.List;

public class DalTest {

    public static void testDal() {

        //_________________________________________________________________________________________
        //_____________________________Users Mock__________________________________________________


//        UsersDal usersDal = new UsersDal();
////        User userMock = new User("yahav@gmail.com","123456789","yahav","tzukerman" ,UserType.CUSTOMER,"054-6261880",null);
//        try {
////            usersDal.addUser(userMock);
////            userMock.setId(34);
////            usersDal.updateUser(userMock);
////            usersDal.deleteUser(31);
////            List<User> users = usersDal.getAllUsers();
////            for(User user : users){
////                System.out.println(user);
////            }
////            System.out.println(usersDal.getUserById(33));
////            System.out.println(usersDal.login("orel@gmail.com", "123456789"));
////            System.out.println(usersDal.getUsersByType(UserType.CUSTOMER));
////            System.out.println(usersDal.isUserExists("orel@gmail.com"));
////            System.out.println(usersDal.getUserByUsername("yahav@gmail.com"));
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }


        //_________________________________________________________________________________________
        //_____________________________Customer Mock________________________________________________

//        userMock.setId(34);
//        CustomersDal customersDal = new CustomersDal();
////        Customer customerMock = new Customer(userMock ,Gender.MALE, MaritalStatus.SINGLE, 0, Date.valueOf("1993-03-02") ,"ISRAEL", "NESHER", "HA-AIRUSIM 24");
////
//        try {
////            customersDal.addCustomer(customerMock);
////            customersDal.deleteCustomer(39);
//            List<Customer> customers = customersDal.getCustomers();
//            for(Customer customer : customers){
//                System.out.println(customer);
//            }
////            customersDal.updateCustomer(customerMock);
////            System.out.println(customersDal.getCustomerById(34));
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }

        //_________________________________________________________________________________________
        //_____________________________Companies Mock________________________________________________

//        CompanyDal companyDal = new CompanyDal();
//        Company companyMock = new Company("Sony","555-666-888", Date.valueOf("1980-01-01"),"Japan","Tokyo","7th street");
//
//        try {
////            companyDal.addCompany(companyMock);
////            System.out.println(companyDal.isCompanyExists("Sony"));
////            companyDal.deleteCompany(4);
////            companyMock.setId(6);
////            companyDal.updateCompany(companyMock);
////            List<Company> companies = companyDal.getAllCompanies();
////            for(Company company : companies){
////                System.out.println(company);
////            }
////            System.out.println(companyDal.getCompanyById(4));
//
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }


        //_________________________________________________________________________________________
        //_____________________________Coupons Mock________________________________________________

//        long now = System.currentTimeMillis();
//        Date endDate = Date.valueOf("2023-01-01");
//        CouponsDal couponsDal = new CouponsDal();
////
//        Coupon couponMock = new Coupon("PS5",6, Category.ELECTRICITY
//                ,"PS5 20% Discount", "discount 20% on Brand new PS5"
//                , new Date(now), endDate, 25, 15f, "ps5_image");
////
//        couponMock.setId(8);
//        try {
////            couponsDal.deleteCoupon(6);
////            couponsDal.addCoupon(couponMock);
////            couponsDal.updateCoupon(couponMock);
////            System.out.println(couponsDal.getCouponById(8));
////            List<Coupon> coupons = couponsDal.getAllCoupons();
////            for(Coupon coupon : coupons){
////                System.out.println(coupon);
////            }
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }


        //_________________________________________________________________________________________
        //_____________________________Purchase Mock________________________________________________

//
//        PurchaseDal purchaseDal = new PurchaseDal();
//        Purchase purchaseMock = new Purchase("yahav@gmail.com","4FAT", 20);
////
//        try {
////            purchaseDal.updatePurchase(purchaseMock);
////            purchaseDal.addPurchase(purchaseMock);
////            purchaseDal.deleteCouponPurchase(27,6);
////            System.out.println(purchaseDal.getPurchaseById(27,6));
////            List<Purchase> purchases = purchaseDal.getAllPurchases();
////            for(Purchase purchase : purchases){
////                System.out.println(purchase);
////            }
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }


    }
}

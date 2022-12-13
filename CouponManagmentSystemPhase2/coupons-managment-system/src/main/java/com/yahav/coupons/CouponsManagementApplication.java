package com.yahav.coupons;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CouponsManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(CouponsManagementApplication.class, args);
    }
}




//    static {
//        Method[] methods = CouponEntity.class.getMethods();
//
//        for (Method method : methods) {
//            System.out.println(method.getName());
//            System.out.println(method.getAnnotations());
//        }
//    }

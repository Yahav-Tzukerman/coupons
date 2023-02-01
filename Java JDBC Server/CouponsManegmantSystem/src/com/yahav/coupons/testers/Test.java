package com.yahav.coupons.testers;

import com.yahav.coupons.beans.*;
import com.yahav.coupons.controllers.*;
import com.yahav.coupons.enums.Category;
import com.yahav.coupons.enums.Gender;
import com.yahav.coupons.enums.MaritalStatus;
import com.yahav.coupons.enums.UserType;
import com.yahav.coupons.tasks.DeleteExpiredCouponsTimerTask;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

import static java.lang.Thread.sleep;

public class Test {
    private static final UserController userController = new UserController();
    private static final CustomerController customerController = new CustomerController();
    private static final CompanyController companyController = new CompanyController();
    private static final CouponController couponController = new CouponController();
    private static final PurchaseController purchaseController = new PurchaseController();
    private static final TimerTask deleteExpiredCouponsTimerTask = new DeleteExpiredCouponsTimerTask();
    private static final Timer timer = new Timer();
    private static final long dayPeriod = 86_400_000; // => 24 Hours (1000 * 60 * 60 * 24)

    private static final List<User> userList = new ArrayList<>();
    private static final List<Customer> customerList = new ArrayList<>();
    private static final List<Company> companiesList = new ArrayList<>();
    private static final List<Coupon> couponList = new ArrayList<>();
    private static final List<Purchase> purchasesList = new ArrayList<>();

    public static void testAll() {
        System.out.println("Welcome to Coupons Management System By Tzukerman Yahav");
        System.out.println("John Bryce Project part 1:");
        ClearContent.deleteAllContent();
        addValuesToDB();
        timer.schedule(deleteExpiredCouponsTimerTask, getExecutionDate(), dayPeriod);
        checkUpdate();
        checkDelete();
        showContent();
        checkLogin();
        timer.cancel();
        System.out.println("Waiting 5 minutes to check login for the suspended user after suspension is over......");
    }

    private static java.util.Date getExecutionDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }


    private static void checkLogin() {
        System.out.println("checking 6 attempts to login with wrong password:");
        for (int i = 0; i < 6; i++) {
            userController.login("yahav@gmail.com", "Uahav1234");
        }
        System.out.println("another user with the right password:");
        userController.login("yahav@icloud.com", "Yahav12345");
        new Thread(() -> {
            try {
                sleep(305_000); // -> 5 minutes and 5 seconds delay (waiting for the user to unsuspended)
                System.out.println("suspended user with the right password after 5 minutes:");
                userController.login("yahav@gmail.com", "Yahav1234");
                System.out.println("All tests were successfully done, Thank you and have a nice day!");
            } catch (InterruptedException e) {
                System.out.println("something went wrong");
            }
        }).start();
    }

    private static void checkUpdate() {
        Company company = companyController.getCompany("Sony");
        if (company != null) {
            company.setAddress("Updated Street");
            companyController.updateCompany(company);
        }

        User user = userController.getUser("admin@admin.com");
        if (user != null) {
            user.setFirstName("Yahav");
            user.setLastName("Tzukerman");
            userController.updateUser(user);
        }

        Purchase purchase = purchaseController.getPurchase("yahav@icloud.com", "PS4");
        if (purchase != null) {
            purchase.setAmount(16);
            purchaseController.updatePurchase(purchase);
        }

        Customer customer = customerController.getCustomer("orel@icloud.com");
        if (customer != null) {
            customer.setMaritalStatus(MaritalStatus.MARRIED);
            customerController.updateCustomer(customer);
        }

        Coupon coupon = couponController.getCoupon("PS4");
        if (coupon != null) {
            coupon.setDescription("25$ discount on brand new PS4");
            couponController.updateCoupon(coupon);
        }
    }


    private static void checkDelete() {
        companyController.deleteCompany("Apple");

        userController.deleteUser("admin1@admin.com");

        userController.deleteUser("sol@icloud.com");

        purchaseController.deletePurchase("orel@icloud.com", "COFFEE");

        customerController.deleteCustomer("sagi@gmail.com");

        couponController.deleteCoupon("PS5");
    }

    private static void showContent() {
        System.out.println("Users:_________________");
        List<User> allUsers = userController.getUsers();
        if (allUsers != null) {
            userList.addAll(allUsers);
            for (User user : userList) {
                System.out.println(userController.getUser(user.getUsername()));
            }
        }

        System.out.println("Users of type company:_________________");
        List<User> allCompanyUsers = userController.getUsersByType(UserType.COMPANY);
        if (allUsers != null) {
            for (User user : allCompanyUsers) {
                System.out.println(user);
            }
        }

        System.out.println("Customers:_________________________");
        List<Customer> allCustomers = customerController.getCustomers();
        if (allCustomers != null) {
            customerList.addAll(allCustomers);
            for (Customer customer : customerList) {
                System.out.println(customerController.getCustomer(customer.getUser().getUsername()));
            }
        }

        System.out.println("Companies:_________________________");
        List<Company> allCompanies = companyController.getCompanies();
        if (allCompanies != null) {
            companiesList.addAll(allCompanies);
            for (Company company : companiesList) {
                System.out.println(companyController.getCompany(company.getName()));
            }
        }

        System.out.println("Coupons:_____________________");
        List<Coupon> allCoupons = couponController.getCoupons();
        if (allCoupons != null) {
            couponList.addAll(allCoupons);
            for (Coupon coupon : couponList) {
                System.out.println(couponController.getCoupon(coupon.getCode()));
            }
        }

        System.out.println("Coupons BY CATEGORY = FOOD:_____________________");
        List<Coupon> couponsByCategory = couponController.getCouponsByCategory(Category.FOOD);
        if (couponsByCategory != null) {
            for (Coupon coupon : couponsByCategory) {
                System.out.println(couponController.getCoupon(coupon.getCode()));
            }
        }

        System.out.println("Purchases:__________________________");
        List<Purchase> allPurchases = purchaseController.getPurchases();
        if (allPurchases != null) {
            purchasesList.addAll(allPurchases);
            for (Purchase purchase : purchasesList) {
                System.out.println(purchaseController.getPurchase(purchase.getUsername(), purchase.getCouponCode()));
            }
        }

        System.out.println("yahav@icloud.com Purchases where 50 is the max price:__________________________");
        List<Purchase> purchasesMaxPrice = purchaseController.getUserPurchasesByMaxPrice("yahav@icloud.com", 50f);
        if (purchasesMaxPrice != null) {
            for (Purchase purchase : purchasesMaxPrice) {
                System.out.println(purchaseController.getPurchase(purchase.getUsername(), purchase.getCouponCode()));
            }
        }
    }

    private static void addValuesToDB() {
        User admin = new User("admin@admin.com", "Admin1234", "avi", null, UserType.ADMINISTRATOR, null, null);
        userController.addUser(admin);

        User admin1 = new User("admin1@admin.com", "Admin1234", null, null, UserType.ADMINISTRATOR, null, null);
        userController.addUser(admin1);

        User customer1User = new User("yahav@gmail.com", "Yahav1234", "Yahav", "Tzukerman", UserType.CUSTOMER, "0546261880", null);
        Customer customer1 = new Customer(customer1User, Gender.MALE, MaritalStatus.SINGLE, 0, Date.valueOf("1993-03-02"), "Israel", "Nesher", "Ha-Airusim 24");
        customerController.addCustomer(customer1);

        User customer2User = new User("yahav@icloud.com", "Yahav12345", null, null, UserType.CUSTOMER, null, null);
        Customer customer2 = new Customer(customer2User, null, null, null, Date.valueOf("1992-01-01"), "USA", "Los-Angeles", "7th street");
        customerController.addCustomer(customer2);

        User customer3User = new User("orel@icloud.com", "Orel159357", "Orel", "Shmueli", UserType.CUSTOMER, "0538254420", null);
        Customer customer3 = new Customer(customer3User, Gender.FEMALE, MaritalStatus.SINGLE, 0, Date.valueOf("1996-01-31"), "Israel", "Nesher", "Ha-Airusim 24");
        customerController.addCustomer(customer3);

        User customer4User = new User("sol@icloud.com", "Sol123456", "Sol", null, UserType.CUSTOMER, null, null);
        Customer customer4 = new Customer(customer4User, Gender.FEMALE, null, null, Date.valueOf("2001-01-01"), "Israel", "Unknown", "Unknown");
        customerController.addCustomer(customer4);

        User customer5User = new User("sagi@gmail.com", "Sagi13245", "Sagi", "Zayger", UserType.CUSTOMER, "0529314512", null);
        Customer customer5 = new Customer(customer5User, Gender.MALE, MaritalStatus.DIVORCED, 3, Date.valueOf("1991-09-10"), "Israel", "Ramat-Gan", "Yadydia-4");
        customerController.addCustomer(customer5);

        Company company1 = new Company("Apple", "1800-275-2273", Date.valueOf("1976-04-01"), "United States", "California", "Los-Altos");
        int company1Id = companyController.addCompany(company1);
        User company1User1 = new User("apple@icloud.com", "Apple12345", "Steve", "Jobs", UserType.COMPANY, "0501234567", company1Id);
        userController.addUser(company1User1);
        User company1User2 = new User("tim@icloud.com", "Apple12345", "Tim", "Cook", UserType.COMPANY, "0501234967", company1Id);
        userController.addUser(company1User2);

        Company company2 = new Company("Sony", "1234568790", Date.valueOf("1965-03-25"), "Japan", "Tokyo", "Tokyo-Square");
        int company2Id = companyController.addCompany(company2);
        User company2User1 = new User("sony@gmail.com", "Sony123456", "Kenichiro", "Yoshida", UserType.COMPANY, "0512345678", company2Id);
        userController.addUser(company2User1);

        Company company3 = new Company("Starbucks", "1593574650", Date.valueOf("2000-05-25"), "USA", "Los-Angeles", "Los-Santos");
        int company3Id = companyController.addCompany(company3);
        User company3User1 = new User("starbucks@gmail.com", "Star159357", "Kevin", "Johnson", UserType.COMPANY, "0512345659", company3Id);
        userController.addUser(company3User1);
        User company3User2 = new User("starbucks-cashier@gmail.com", "Star159357", "Tina", "Kalman", UserType.COMPANY, "0512345652", company3Id);
        userController.addUser(company3User2);

        Date currentDate = Date.valueOf(LocalDate.now());

        Coupon coupon1 = new Coupon("MAC4U", company1.getName(), Category.ELECTRICITY, "Macbook discount", "500$ discount on brand new Macbook pro", currentDate, Date.valueOf(currentDate.toLocalDate().plusDays(150)), 50, 150, "macbook-image.png");
        couponController.addCoupon(coupon1);

        Coupon coupon2 = new Coupon("I4ON", company1.getName(), Category.ELECTRICITY, "Iphone discount", "150$ discount on brand new Iphone pro", currentDate, Date.valueOf(currentDate.toLocalDate().plusDays(100)), 80, 75, "iphone-image.png");
        couponController.addCoupon(coupon2);

        Coupon coupon3 = new Coupon("PS5", company2.getName(), Category.ELECTRICITY, "Playstation 5 discount", "100$ discount on brand new PS5", currentDate, Date.valueOf(currentDate.toLocalDate().plusDays(350)), 30, 60, "PS5-image.png");
        couponController.addCoupon(coupon3);

        Coupon coupon4 = new Coupon("PS4", company2.getName(), Category.ELECTRICITY, "Playstation 4 discount", "50$ discount on brand new PS4", currentDate, Date.valueOf(currentDate.toLocalDate().plusDays(35)), 35, 35, "PS4-image.png");
        couponController.addCoupon(coupon4);

        Coupon coupon5 = new Coupon("COFFEE", company3.getName(), Category.FOOD, "Free coffee", "Free coffee on your next Starbucks purchase", currentDate, Date.valueOf(currentDate.toLocalDate().plusDays(25)), 250, 2, "coffee-image.png");
        couponController.addCoupon(coupon5);

        Coupon coupon6 = new Coupon("CROISSANT", company3.getName(), Category.FOOD, "Free Croissant", "Free Croissant on your next Starbucks purchase", currentDate, Date.valueOf(currentDate.toLocalDate().plusDays(60)), 250, 2, "croissant-Image.png");
        couponController.addCoupon(coupon6);

        Coupon coupon7 = new Coupon("SONY4K", company3.getName(), Category.ELECTRICITY, "Sony 4K TV discount", "250$ discount on brand new Sony 4K TV", currentDate, Date.valueOf(currentDate.toLocalDate().plusDays(100)), 1000, 100, "SONY-4KTV-image.png");
        couponController.addCoupon(coupon7);

        Coupon coupon8 = new Coupon("DESSERT", company3.getName(), Category.FOOD, "Free Dessert", "Free Dessert on your next Starbucks purchase", Date.valueOf("2020-01-01"), Date.valueOf("2020-07-01"), 200, 2, "dessert-image.png");
        couponController.addInvalidCouponForJobTest(coupon8);

        Coupon coupon9 = new Coupon("IMAC", company1.getName(), Category.ELECTRICITY, "Imac discount", "50$ discount on brand new Imac", Date.valueOf("2020-01-01"), Date.valueOf("2020-07-01"), 200, 250, "IMAC-image.png");
        couponController.addInvalidCouponForJobTest(coupon9);

        Coupon coupon10 = new Coupon("HEADSET", company2.getName(), Category.ELECTRICITY, "Sony Headset discount", "50$ discount on brand new Sony Headset", Date.valueOf("2020-01-01"), Date.valueOf("2020-07-01"), 200, 250, "headset-image.png");
        couponController.addInvalidCouponForJobTest(coupon10);

        Purchase purchase1 = new Purchase("yahav@gmail.com", "Mac4U", 1);
        purchaseController.addPurchase(purchase1);

        Purchase purchase2 = new Purchase("orel@icloud.com", "I4on", 3);
        purchaseController.addPurchase(purchase2);

        Purchase purchase3 = new Purchase("sagi@gmail.com", "PS5", 2);
        purchaseController.addPurchase(purchase3);

        Purchase purchase4 = new Purchase("sol@icloud.com", "PS4", 6);
        purchaseController.addPurchase(purchase4);

        Purchase purchase5 = new Purchase("orel@icloud.com", "COFFEE", 20);
        purchaseController.addPurchase(purchase5);

        Purchase purchase6 = new Purchase("yahav@icloud.com", "SONY4K", 3);
        purchaseController.addPurchase(purchase6);

        Purchase purchase7 = new Purchase("yahav@gmail.com", "PS5", 6);
        purchaseController.addPurchase(purchase7);

        Purchase purchase8 = new Purchase("yahav@icloud.com", "CROISSANT", 10);
        purchaseController.addPurchase(purchase8);

        Purchase purchase9 = new Purchase("yahav@icloud.com", "PS4", 5);
        purchaseController.addPurchase(purchase9);

        Purchase purchase10 = new Purchase("orel@icloud.com", "MAC4U", 5);
        purchaseController.addPurchase(purchase10);
    }
}
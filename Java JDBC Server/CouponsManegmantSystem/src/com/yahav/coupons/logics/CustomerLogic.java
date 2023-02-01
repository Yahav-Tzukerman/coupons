package com.yahav.coupons.logics;

import com.yahav.coupons.beans.Customer;
import com.yahav.coupons.beans.User;
import com.yahav.coupons.dal.CustomersDal;
import com.yahav.coupons.enums.ErrorType;
import com.yahav.coupons.enums.UserType;
import com.yahav.coupons.exceptions.ServerException;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomerLogic {
    private static final CustomersDal customersDal = new CustomersDal();
    private static final UserLogic userLogic = new UserLogic();
    private static final PurchaseLogic purchaseLogic = new PurchaseLogic();

    public CustomerLogic() {
    }

    public int updateCustomer(Customer customer) throws ServerException {
        try {
            validateCustomer(customer);
            return customersDal.updateCustomer(customer);
        } catch (ServerException e) {
            throw new ServerException(e, ErrorType.UPDATE_ERROR, "Failed to update customer data for: " + customer.getUser().getUsername());
        }

    }

    public Customer getCustomer(String username) throws ServerException {
        User user = userLogic.getUser(username);
        return getCustomer(user.getId());
    }

    public Customer getCustomer(int userId) throws ServerException {
        try {
            return customersDal.getCustomer(userId);
        } catch (ServerException e) {
            throw new ServerException(e, ErrorType.GET_OBJECT_ERROR, "Failed to get customer data");
        }
    }

    public List<Customer> getCustomers() throws ServerException {
        try {
            return customersDal.getCustomers();
        } catch (ServerException e) {
            throw new ServerException(e, ErrorType.GET_OBJECT_ERROR, "Failed to get all customers");
        }
    }

    public void clearContent() throws ServerException {
        try {
            customersDal.clearContent();
        } catch (ServerException e) {
            throw new ServerException(e, ErrorType.GENERAL_ERROR, "Failed to clear users content");
        }
    }

    public int deleteCustomer(String username) throws ServerException {
        Customer customer = getCustomer(username);
        if (customer == null) {
            throw new ServerException(ErrorType.CUSTOMER_DOES_NOT_EXIST);
        }
        return deleteCustomer(customer.getUser().getId());
    }

    public int deleteCustomer(int userId) throws ServerException {
        int result = 0;
        try {
            Customer customer = getCustomer(userId);
            if (customer == null) {
                throw new ServerException(ErrorType.CUSTOMER_DOES_NOT_EXIST);
            }
            User user = customer.getUser();
            userId = user.getId();
            String username = user.getUsername();

            purchaseLogic.deleteUserPurchases(username);
            customersDal.deleteCustomer(userId);
            System.out.println(username + " customer data was deleted successfully");

            result = userLogic.deleteUser(userId);

        } catch (ServerException e) {
            throw new ServerException(e, ErrorType.DELETION_ERROR, "Failed to delete customer data");
        }
        return result;
    }

    public void addCustomer(Customer customer) throws ServerException {
        try {
            if (customersDal.isCustomerExist(customer.getUser().getUsername())) {
                throw new ServerException(ErrorType.CUSTOMER_ALREADY_EXIST);
            }
            customer.getUser().setUserType(UserType.CUSTOMER);
            validateCustomer(customer);
            int id = userLogic.addUser(customer.getUser());
            customer.getUser().setId(id);
            customersDal.addCustomer(customer);
        } catch (ServerException e) {
            throw new ServerException(e, ErrorType.CREATION_ERROR, "Failed to add customer data for: " + customer.getUser().getUsername());
        }
    }

    private void validateCustomer(Customer customer) throws ServerException {
        if (customer.getAmountOfKids() != null && (customer.getAmountOfKids() < 0 || customer.getAmountOfKids() > 15)) {
            throw new ServerException(ErrorType.INVALID_FILED, "Amount of kids is Invalid");
        }
        if (customer.getBirthDate().before(Date.valueOf("1900-01-01")) || customer.getBirthDate().after(Date.valueOf(LocalDate.now()))) {
            throw new ServerException(ErrorType.INVALID_DATE, "Birthdate is Invalid");
        }
        if (!nameValidation(customer.getCountry())) {
            throw new ServerException(ErrorType.INVALID_FILED, "Country is Invalid");
        }
        if (!nameValidation(customer.getCity())) {
            throw new ServerException(ErrorType.INVALID_FILED, "City is Invalid");
        }
        if (!nameValidation(customer.getAddress())) {
            throw new ServerException(ErrorType.INVALID_FILED, "Address is Invalid");
        }
    }

    private boolean nameValidation(String name) {
        String regex = "^[A-Za-z0-9-_.\\s]{2,30}+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }
}
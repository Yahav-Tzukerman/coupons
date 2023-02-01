package com.yahav.coupons.controllers;

import com.yahav.coupons.beans.Customer;
import com.yahav.coupons.exceptions.ServerException;
import com.yahav.coupons.logics.CustomerLogic;

import java.util.ArrayList;
import java.util.List;

public class CustomerController {
    private static final CustomerLogic customerLogic = new CustomerLogic();

    public void updateCustomer(Customer customer) {
        try {
            int result = customerLogic.updateCustomer(customer);
            if (result != 0) {
                System.out.println(customer.getUser().getUsername() + ": Customer was updated successfully");
            }
        } catch (ServerException e) {
            System.out.println(e.getMessage());
        }
    }

    public Customer getCustomer(String username) {
        Customer customer = null;
        try {
            customer = customerLogic.getCustomer(username);
        } catch (ServerException e) {
            System.out.println(e.getMessage());
        }
        return customer;
    }

    public Customer getCustomer(int userId) {
        Customer customer = null;
        try {
            customer = customerLogic.getCustomer(userId);
        } catch (ServerException e) {
            System.out.println(e.getMessage());
        }
        return customer;
    }

    public List<Customer> getCustomers() {
        List<Customer> customers = new ArrayList<>();
        try {
            customers = customerLogic.getCustomers();
        } catch (ServerException e) {
            System.out.println(e.getMessage());
        }
        return customers;
    }

    public void deleteCustomer(Customer customer) {
        deleteCustomer(customer.getUser().getUsername());
    }

    public void deleteCustomer(String username) {
        try {
            int result = customerLogic.deleteCustomer(username);
            if (result != 0) {
                System.out.println(username + ": User was deleted successfully");
            }
        } catch (ServerException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteCustomer(int userId) {
        try {
            int result = customerLogic.deleteCustomer(userId);
            if (result != 0) {
                System.out.println(userId + ": User was deleted successfully");
            }
        } catch (ServerException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addCustomer(Customer customer) {
        try {
            customerLogic.addCustomer(customer);
            System.out.println(customer.getUser().getUsername() + ": Customer was added successfully");
        } catch (ServerException e) {
            System.out.println(e.getMessage());
        }
    }
}
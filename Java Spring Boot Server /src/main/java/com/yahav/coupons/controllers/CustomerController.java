package com.yahav.coupons.controllers;

import com.yahav.coupons.exceptions.ServerException;
import com.yahav.coupons.models.CustomerRequestModel;
import com.yahav.coupons.models.CustomerResponseModel;
import com.yahav.coupons.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(@Lazy CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public CustomerResponseModel addCustomer(@RequestBody CustomerRequestModel customerRequestModel) throws ServerException {
        return customerService.addCustomer(customerRequestModel);
    }

    @PutMapping
    public CustomerResponseModel updateCustomer(@RequestBody CustomerRequestModel customerRequestModel, HttpServletRequest request) throws ServerException {
        return customerService.updateCustomer(customerRequestModel, request);
    }

    @GetMapping
    public Page<CustomerResponseModel> getCustomers(@RequestParam(defaultValue = "0") @Min(value = 0) int pageNumber
            , @RequestParam(defaultValue = "15") @Min(value = 1) @Max(value = 100) int pageSize) {
        return customerService.getCustomers(pageSize, pageNumber);
    }

    @GetMapping("/{id}")
    public CustomerResponseModel getCustomer(@PathVariable("id") long id, HttpServletRequest request) throws ServerException {
        return customerService.getCustomer(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable("id") long id, HttpServletRequest request) throws ServerException {
        customerService.deleteCustomer(id, request);
    }
}

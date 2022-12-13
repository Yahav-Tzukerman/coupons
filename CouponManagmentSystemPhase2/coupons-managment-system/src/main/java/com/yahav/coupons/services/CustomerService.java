package com.yahav.coupons.services;

import com.yahav.coupons.converters.CustomerEntityToCustomerResponseModelConverter;
import com.yahav.coupons.converters.CustomerRequestModelToCustomerEntityConverter;
import com.yahav.coupons.entities.CustomerEntity;
import com.yahav.coupons.entities.UserEntity;
import com.yahav.coupons.enums.ErrorType;
import com.yahav.coupons.enums.Role;
import com.yahav.coupons.enums.Status;
import com.yahav.coupons.exceptions.ServerException;
import com.yahav.coupons.models.CustomerRequestModel;
import com.yahav.coupons.models.CustomerResponseModel;
import com.yahav.coupons.repositories.CustomerRepository;
import com.yahav.coupons.security.jwt.JwtUserValidator;
import com.yahav.coupons.validations.RegexValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.time.LocalDate;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUserValidator jwtUserValidator;
    private final CustomerEntityToCustomerResponseModelConverter customerEntityToCustomerResponseModelConverter;
    private final CustomerRequestModelToCustomerEntityConverter customerRequestModelToCustomerEntityConverter;

    @Autowired
    public CustomerService(CustomerRepository customerRepository
            , @Lazy UserService userService, @Lazy PasswordEncoder passwordEncoder
            , @Lazy JwtUserValidator jwtUserValidator, @Lazy CustomerEntityToCustomerResponseModelConverter customerEntityToCustomerResponseModelConverter
            , @Lazy CustomerRequestModelToCustomerEntityConverter customerRequestModelToCustomerEntityConverter) {
        this.customerRepository = customerRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUserValidator = jwtUserValidator;
        this.customerEntityToCustomerResponseModelConverter = customerEntityToCustomerResponseModelConverter;
        this.customerRequestModelToCustomerEntityConverter = customerRequestModelToCustomerEntityConverter;
    }

    // Public
    public CustomerResponseModel addCustomer(CustomerRequestModel customerRequestModel) throws ServerException {
        if (userService.isUserExists(customerRequestModel.getUser().getUsername())) {
            throw new ServerException(ErrorType.USERNAME_ALREADY_EXIST);
        }
        return saveCustomer(customerRequestModel);
    }

    // Admin or Validated Customer
    public CustomerResponseModel updateCustomer(CustomerRequestModel customerRequestModel, HttpServletRequest request) throws ServerException {
        if (!userService.isUserExists(customerRequestModel.getUser().getUsername())) {
            throw new ServerException(ErrorType.USER_DOES_NOT_EXIST);
        }
        jwtUserValidator.validateIdFromJWT(customerRequestModel.getUser().getId(), request);
        return saveCustomer(customerRequestModel);
    }

    private CustomerResponseModel saveCustomer(CustomerRequestModel customerRequestModel) throws ServerException {
        validateCustomer(customerRequestModel);
        customerRequestModel.getUser().setRole(Role.CUSTOMER);
        CustomerEntity customerEntity = customerRequestModelToCustomerEntityConverter.convert(customerRequestModel);
        if (customerEntity == null) {
            throw new ServerException(ErrorType.GENERAL_ERROR);
        }
        customerEntity.getUser().setPassword(passwordEncoder.encode(customerEntity.getUser().getPassword()));
        if (customerEntity.getUser().getId() == 0) {
            customerEntity.getUser().setJoinDate(LocalDate.now());
            customerEntity.getUser().setAmountOfFailedLogins(0);
            customerEntity.getUser().setStatus(Status.ACTIVE);
        } else {
            UserEntity userEntity = userService.getUserEntity(customerRequestModel.getUser().getId());
            customerEntity.getUser().setJoinDate(userEntity.getJoinDate());
            customerEntity.getUser().setAmountOfFailedLogins(userEntity.getAmountOfFailedLogins());
            customerEntity.getUser().setStatus(userEntity.getStatus());
        }
        customerEntity = customerRepository.save(customerEntity);
        return customerEntityToCustomerResponseModelConverter.convert(customerEntity);
    }

    // Admin or Validated Customer
    public CustomerResponseModel getCustomer(long id, HttpServletRequest request) throws ServerException {
        jwtUserValidator.validateIdFromJWT(id, request);
        return customerRepository.getCustomer(id);
    }

    // Only Admin
    public Page<CustomerResponseModel> getCustomers(int pageSize, int pageNumber) {
        Page<CustomerEntity> customerEntityPage = customerRepository.findAll(Pageable.ofSize(pageSize).withPage(pageNumber));
        return customerEntityPage.map(customerEntityToCustomerResponseModelConverter::convert);
    }

    // Admin or Validated Customer
    public void deleteCustomer(long id, HttpServletRequest request) throws ServerException {
        jwtUserValidator.validateIdFromJWT(id, request);
        customerRepository.deleteById(id);
    }

    private void validateCustomer(CustomerRequestModel customerRequestModel) throws ServerException {
        if ((customerRequestModel.getAmountOfChildren() != null) && (customerRequestModel.getAmountOfChildren() < 0 || customerRequestModel.getAmountOfChildren() > 15)) {
            throw new ServerException(ErrorType.INVALID_AMOUNT_OF_KIDS);
        }
        if ((customerRequestModel.getBirthDate() != null) && (customerRequestModel.getBirthDate().isBefore(Date.valueOf("1900-01-01").toLocalDate()) || customerRequestModel.getBirthDate().isAfter(LocalDate.now()))) {
            throw new ServerException(ErrorType.INVALID_BIRTHDATE);
        }
        if (!customerRequestModel.getAddress().equals("") && !RegexValidations.nameValidation(customerRequestModel.getAddress())) {
            throw new ServerException(ErrorType.INVALID_ADDRESS, "Address is Invalid");
        }
        if (!RegexValidations.emailValidation(customerRequestModel.getUser().getUsername())) {
            throw new ServerException(ErrorType.INVALID_USERNAME);
        }
        if (!RegexValidations.passwordValidation(customerRequestModel.getUser().getPassword())) {
            throw new ServerException(ErrorType.INVALID_PASSWORD);
        }
        if (customerRequestModel.getUser().getFirstName() != null && !customerRequestModel.getUser().getFirstName().equals("")  && !RegexValidations.nameValidation(customerRequestModel.getUser().getFirstName())) {
            throw new ServerException(ErrorType.INVALID_NAME, "Invalid first name");
        }
        if (customerRequestModel.getUser().getLastName() != null && !customerRequestModel.getUser().getLastName().equals("") && !RegexValidations.nameValidation(customerRequestModel.getUser().getLastName())) {
            throw new ServerException(ErrorType.INVALID_NAME, "Invalid last name");
        }
        if (customerRequestModel.getUser().getPhone() != null && !customerRequestModel.getUser().getPhone().equals("") && !RegexValidations.phoneValidation(customerRequestModel.getUser().getPhone())) {
            throw new ServerException(ErrorType.INVALID_PHONE_NUMBER);
        }
    }
}

package com.yahav.coupons.controllers;

import com.yahav.coupons.enums.Role;
import com.yahav.coupons.exceptions.ServerException;
import com.yahav.coupons.models.UserChangPasswordModel;
import com.yahav.coupons.models.UserLoginData;
import com.yahav.coupons.models.UserRequestModel;
import com.yahav.coupons.models.UserResponseModel;
import com.yahav.coupons.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(@Lazy UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public UserResponseModel login(@RequestBody UserLoginData userLoginData) throws ServerException {
        return userService.login(userLoginData);
    }

    @PostMapping
    public UserResponseModel register(@RequestBody UserRequestModel userRequestModel) throws ServerException {
        return userService.addUser(userRequestModel);
    }

    @PutMapping
    public UserResponseModel updateUser(@RequestBody UserRequestModel userRequestModel, HttpServletRequest request) throws ServerException {
        return userService.updateUser(userRequestModel, request);
    }

    @PutMapping("/changePassword")
    public UserResponseModel changePassword(@RequestBody UserChangPasswordModel userChangPasswordModel
            , HttpServletRequest request) throws ServerException {
        return userService.changePassword(userChangPasswordModel.getOldPassword(), userChangPasswordModel.getNewPassword(), request);
    }

    @GetMapping
    public Page<UserResponseModel> getUsers(@RequestParam(defaultValue = "0") @Min(value = 0) int pageNumber
            , @RequestParam(defaultValue = "15") @Min(value = 1) @Max(value = 100) int pageSize) {
        return userService.getUsers(pageSize, pageNumber);
    }

    @GetMapping("/{id}")
    public UserResponseModel getUser(@PathVariable("id") long id, HttpServletRequest request) throws ServerException {
        return userService.getUserRequest(id, request);
    }

    @GetMapping("/companyUsers")
    public Page<UserResponseModel> getCompanyUsers(@RequestParam("companyId") long companyId
            , @RequestParam(defaultValue = "0") @Min(value = 0) int pageNumber
            , @RequestParam(defaultValue = "15") @Min(value = 1) @Max(value = 100) int pageSize
            , HttpServletRequest request) throws ServerException {
        return userService.getCompanyUsers(companyId, pageSize, pageNumber, request);
    }

    @GetMapping("/byRole")
    public Page<UserResponseModel> getUsersByRole(@RequestParam("role") Role role
            , @RequestParam(defaultValue = "0") @Min(value = 0) int pageNumber
            , @RequestParam(defaultValue = "15") @Min(value = 1) @Max(value = 100) int pageSize) {
        return userService.getUsersByRole(role, pageSize, pageNumber);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") long id, HttpServletRequest request) throws ServerException {
        userService.deleteUser(id, request);
    }
}

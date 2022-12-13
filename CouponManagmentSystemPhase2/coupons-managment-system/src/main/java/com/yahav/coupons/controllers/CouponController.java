package com.yahav.coupons.controllers;

import com.yahav.coupons.exceptions.ServerException;
import com.yahav.coupons.models.CouponModel;
import com.yahav.coupons.services.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("/coupons")
public class CouponController {

    private final CouponService couponService;

    @Autowired
    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @PostMapping
    public CouponModel addCoupon(@RequestBody CouponModel couponModel, HttpServletRequest request) throws ServerException {
        return couponService.addCoupon(couponModel, request);
    }

    @PutMapping
    public CouponModel updateCoupon(@RequestBody CouponModel couponModel, HttpServletRequest request) throws ServerException {
        return couponService.updateCoupon(couponModel, request);
    }

    @GetMapping
    public Page<CouponModel> getCoupons(@RequestParam(required = false, defaultValue = "") String filter
                                      , @RequestParam(defaultValue = "0") @Min(value = 0) int pageNumber
                                      , @RequestParam(defaultValue = "15") @Min(value = 1) @Max(value = 100) int pageSize
                                      , @RequestParam(required = false, defaultValue = "0") int companyId
                                      , @RequestParam(required = false, defaultValue = "0") int maxPrice
                                      , @RequestParam(required = false, defaultValue = "") String category
                                      , @RequestParam (required = false, defaultValue = "") String searchedTitle
                                    , @RequestParam (required = false, defaultValue = "price") String sortBy
                                    , @RequestParam (required = false, defaultValue = "false") boolean isDescending) throws Exception {
return couponService.getCouponsWithFilter(filter, pageSize, pageNumber, companyId, maxPrice, category, searchedTitle, sortBy, isDescending);
    }

    @GetMapping("/{id}")
    public CouponModel getCoupon(@PathVariable(name = "id") long id) {
        return couponService.getCouponRequest(id);
    }

    @DeleteMapping("/{id}")
    public void deleteCoupon(@PathVariable(name = "id") long id, HttpServletRequest request) throws ServerException {
        couponService.removeCouponRequest(id, request);
    }
}
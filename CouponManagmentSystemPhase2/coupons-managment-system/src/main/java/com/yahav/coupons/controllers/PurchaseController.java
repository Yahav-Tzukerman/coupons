package com.yahav.coupons.controllers;

import com.yahav.coupons.exceptions.ServerException;
import com.yahav.coupons.models.PurchaseRequestModel;
import com.yahav.coupons.models.PurchaseResponseModel;
import com.yahav.coupons.models.Revenue;
import com.yahav.coupons.services.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/purchases")
public class PurchaseController {

    private final PurchaseService purchaseService;

    @Autowired
    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping
    public PurchaseResponseModel addPurchase(@RequestBody PurchaseRequestModel purchaseRequestModel, HttpServletRequest request) throws ServerException {
        return purchaseService.savePurchase(purchaseRequestModel, request);
    }

    @GetMapping
    public Page<PurchaseResponseModel> getAllPurchases(@RequestParam(defaultValue = "0") @Min(value = 0) int pageNumber
            , @RequestParam(defaultValue = "15") @Min(value = 1) @Max(value = 100) int pageSize) {
        return purchaseService.getAllPurchases(pageSize, pageNumber);
    }

    @GetMapping("/amount")
    public float getAllPurchasesAmount() {
        return purchaseService.getAllPurchasesAmount();
    }

    @GetMapping("/byUser/{id}")
    public Page<PurchaseResponseModel> getPurchasesByUserId(@PathVariable(name = "id") long id
            , @RequestParam(defaultValue = "0") @Min(value = 0) int pageNumber
            , @RequestParam(defaultValue = "15") @Min(value = 1) @Max(value = 100) int pageSize
            , HttpServletRequest request) throws ServerException {
        return purchaseService.getAllPurchasesByUserId(id, pageSize, pageNumber, request);
    }

    @GetMapping("/byCoupon/{id}")
    public Page<PurchaseResponseModel> getPurchasesByCouponId(@PathVariable(name = "id") long id
            , @RequestParam(defaultValue = "0") @Min(value = 0) int pageNumber
            , @RequestParam(defaultValue = "15") @Min(value = 1) @Max(value = 100) int pageSize
            , HttpServletRequest request) throws ServerException {
        return purchaseService.getAllPurchasesByCouponId(id, pageSize, pageNumber, request);
    }

    @GetMapping("/byCompany/{id}")
    public Page<PurchaseResponseModel> getPurchasesByCompanyId(@PathVariable(name = "id") long id
            , @RequestParam(defaultValue = "0") @Min(value = 0) int pageNumber
            , @RequestParam(defaultValue = "15") @Min(value = 1) @Max(value = 100) int pageSize
            , HttpServletRequest request) throws ServerException {
        return purchaseService.getAllPurchasesByCompanyId(id, pageSize, pageNumber, request);
    }

    @GetMapping("/{id}")
    public PurchaseResponseModel getPurchase(@PathVariable(name = "id") long id, HttpServletRequest request) throws ServerException {
        return purchaseService.getPurchase(id, request);
    }

    @GetMapping("/revenue")
    public List<Revenue> getRevenue() {
        return purchaseService.getRevenue();
    }

    @DeleteMapping("/{id}")
    public void deletePurchase(@PathVariable(name = "id") long id, HttpServletRequest request) throws ServerException {
        purchaseService.deletePurchase(id, request);
    }
}

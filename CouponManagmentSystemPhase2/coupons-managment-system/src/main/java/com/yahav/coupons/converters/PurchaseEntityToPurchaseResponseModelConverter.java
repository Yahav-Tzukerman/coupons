package com.yahav.coupons.converters;

import com.sun.istack.NotNull;
import com.yahav.coupons.entities.PurchaseEntity;
import com.yahav.coupons.models.PurchaseResponseModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PurchaseEntityToPurchaseResponseModelConverter implements Converter<PurchaseEntity, PurchaseResponseModel> {

    @Override
    public PurchaseResponseModel convert(@NotNull PurchaseEntity purchaseEntity) {
        PurchaseResponseModel purchaseResponseModel = new PurchaseResponseModel();
        purchaseResponseModel.setId(purchaseEntity.getId());
        purchaseResponseModel.setCouponId(purchaseEntity.getCoupon().getId());
        purchaseResponseModel.setUserId(purchaseEntity.getUser().getId());
        purchaseResponseModel.setPurchaseTimestamp(purchaseEntity.getPurchaseTimestamp());
        purchaseResponseModel.setAmount(purchaseEntity.getAmount());
        purchaseResponseModel.setTotalPrice(purchaseEntity.getTotalPrice());
        return purchaseResponseModel;
    }
}

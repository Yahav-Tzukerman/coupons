import React, { useEffect, useState } from "react"
import { ICoupon } from "../../../../models/ICoupon";
import { IPurchaseRequest } from "../../../../models/IPurchaseRequest";
import CouponsService from "../../../../services/coupon.service";

interface ICheckoutItemProps {
    couponId: number
    amount?: number
}

const CheckoutItem = ({ couponId, amount }: ICheckoutItemProps) => {

    let purchaseObj: IPurchaseRequest = {
        userId: 1,
        couponId: 1,
        amount: 1
    }

    const [coupon, setCoupon] = useState<ICoupon>();
    let price: number = (amount && coupon) ? coupon?.price * amount : 0;

    useEffect(() => {
        CouponsService.getCouponById(couponId).then(response => setCoupon(response.data));
    }, []);

    return (
        <div className="checkout">
            <h5>{coupon?.title}</h5>
            <h5>{`${price}$`}</h5>
            <h5>{amount}</h5>
        </div>
    );
}

export default CheckoutItem
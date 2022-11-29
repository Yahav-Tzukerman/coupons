import React, { useEffect, useState } from "react"
import { ICoupon } from "../../../../models/ICoupon";
import CouponsService from "../../../../services/coupon.service";

interface ICheckoutItemProps {
    couponId: number
    amount?: number
}

const CheckoutItem = ({ couponId, amount }: ICheckoutItemProps) => {

    const [coupon, setCoupon] = useState<ICoupon>();

    useEffect(() => {
        CouponsService.getCouponById(couponId).then(response => setCoupon(response.data));
    }, []);

    return (
        <div className="checkout">
            <h5>{coupon?.title}</h5>
            <h5>{`${coupon?.price}$`}</h5>
            <h5>{amount}</h5>
        </div>
    );
}

export default CheckoutItem
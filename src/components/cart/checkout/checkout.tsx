import React, { useEffect, useState } from "react"
import { useSelector } from "react-redux";
import { IPurchaseRequest } from "../../../models/IPurchaseRequest";
import { AppState } from "../../../redux/app-state";
import CouponsService from "../../../services/coupon.service";
import CheckoutItem from "./checkout-item/checkoutItem";

const Checkout = () => {

    const cart: IPurchaseRequest[] = useSelector((state: AppState) => state.cart);

    const [total, setTotal] = useState(0);
    let count = 0;



    useEffect(() => {

        cart.forEach(async cartItem => {
            let coupon = await CouponsService.getCouponById(cartItem.couponId).then(response => {
                let x = (response.data.price * cartItem.amount);
                count = count + x;
                setTotal(count);
            });
            // 
        })


    }, []);


    return (
        <div className="checkout">
            {cart.map(cartItem => (
                <div key={cartItem.couponId} className="checkout-item" >
                    <CheckoutItem
                        couponId={cartItem.couponId}
                        amount={cartItem.amount} />
                </div>
            ))}
            <h1>{`TOTAL PRICE: ${total}$`}</h1>
            <input type="button" value="confirm purchse" />
        </div>
    );
}

export default Checkout
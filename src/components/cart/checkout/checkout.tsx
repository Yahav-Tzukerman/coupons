import React, { useEffect, useState } from "react"
import { Col, Row } from "react-bootstrap";
import { useSelector } from "react-redux";
import { IPurchaseRequest } from "../../../models/IPurchaseRequest";
import { AppState } from "../../../redux/app-state";
import CouponsService from "../../../services/coupon.service";
import PurchaseService from "../../../services/purchase.service";
import CheckoutItem from "./checkout-item/checkoutItem";
import '../../../assets/styles.css'
import './invoice.css'

const Checkout = () => {

    let purchaseObj: IPurchaseRequest = {
        userId: 1,
        couponId: 1,
        amount: 1
    }

    var today = new Date();
    var time = today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds();
    var date = today.getFullYear() + '-' + (today.getMonth() + 1) + '-' + today.getDate();

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
        })
    }, []);

    function onPurchase() {
        for (let i = 0; i < cart.length; i++) {
            PurchaseService.purchase(cart[i] ? cart[i] : purchaseObj).then((response) => {
                console.log(response.data);
                localStorage.setItem('purchase', JSON.stringify(response.data));

            }).catch((error) => {
                localStorage.setItem('purchaseError', JSON.stringify(error.response));
                alert(error.response.data.message)
            });
        }
        localStorage.removeItem('cart');
        window.location.reload();
    }


    return (
        <div className="wrapper">
            <div className="invoice-wrapper">
                <div className="invoice-header">
                    <div className="logo-invoice-wrap">
                        <div className="logo-sec">
                            <img src='./logo-black.png' />
                        </div>
                        <div className="invoice-sec">
                            <p className="date">
                                <h6 className="invoice-date">insert user first + last name</h6>
                            </p>
                            <p className="date">
                                <h6 className="invoice-date">Date: {date}</h6>
                            </p>
                            <p className="date">
                                <h6 className="invoice-date">Time: {time}</h6>
                            </p>
                        </div>
                    </div>
                </div>
                <div className="invoice-body">
                    <div className="invoice-body-border">
                        <div className="invoice-head-desc">
                            <Row>
                                <Col sm={7}>
                                    <div className="title-invoice-header">
                                        <h5 className="invoice-head">
                                            item description
                                        </h5>
                                    </div>
                                </Col>
                                <Col sm={2}>
                                    <div className="amt-invoice-header">
                                        <h5 className="invoice-head">
                                            amount
                                        </h5>
                                    </div>
                                </Col>
                                <Col sm={3}>
                                    <div className="price-invoice-header">
                                        <h5 className="invoice-head">
                                            price
                                        </h5>
                                    </div>
                                </Col>
                            </Row>
                        </div>
                        {cart.map(cartItem => (
                            <div key={cartItem.couponId} >
                                <CheckoutItem
                                    couponId={cartItem.couponId}
                                    amount={cartItem.amount} />
                            </div>
                        ))}
                        <div className="pay">

                            <h1 className="invoice-total-price">{`Total Price: ${total}$`}</h1>
                            <input type="button" value="confirm-purchase" id="confirm-purchase-btn" onClick={onPurchase} />
                        </div>
                    </div>
                </div>
            </div>
        </div>














    );
}

export default Checkout



















{/* 
                <div className="invoice">
                    <div className="head-invoice">
                        <Row>
                            <Col sm={4}>
                                <div className="title-invoice-header">
                                    <h5 className="invoice-head">
                                        item description
                                    </h5>
                                </div>
                            </Col>
                            <Col sm={4}>
                                <div className="amt-invoice-header">
                                    <h5 className="invoice-head">
                                        amount
                                    </h5>
                                </div>
                            </Col>
                            <Col sm={4}>
                                <div className="price-invoice-header">
                                    <h5 className="invoice-head">
                                        price
                                    </h5>
                                </div>
                            </Col>
                        </Row>
                    </div>

                    <div className="checkout-chk">
                        {cart.map(cartItem => (
                            <div key={cartItem.couponId} >
                                <CheckoutItem
                                    couponId={cartItem.couponId}
                                    amount={cartItem.amount} />
                            </div>
                        ))}
                        <h1>{`TOTAL PRICE: ${total}$`}</h1>
                        <input type="button" value="confirm purchse" onClick={onPurchase} />
                    </div>

                </div > */}
import React, { useEffect, useState } from "react"
import { Col, Row } from "react-bootstrap";
import { ICoupon } from "../../../../models/ICoupon";
import { IPurchaseRequest } from "../../../../models/IPurchaseRequest";
import CouponsService from "../../../../services/coupon.service";
import './checkout.css'
import '../../../../assets/styles.css'

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
            <div className="checkout-item">
                <Row>
                    <Col sm={7}>
                        <div className="title-invoice">
                            <h5 className="invoice">
                                {coupon?.title}
                            </h5>
                        </div>
                    </Col>
                    <Col sm={2}>
                        <div className="amt-invoice">
                            <h5 className="invoice">
                                {amount}
                            </h5>
                        </div>
                    </Col>
                    <Col sm={3}>
                        <div className="price-invoice">
                            <h5 className="invoice">
                                {`${price}$`}
                            </h5>
                        </div>
                    </Col>
                </Row>
            </div> 
        </div>
    );
}

export default CheckoutItem
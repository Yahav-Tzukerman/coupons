import { useEffect, useState } from "react";
import { Col, Row, Image } from "react-bootstrap";
import { ICoupon } from "../../../models/ICoupon";
import CouponsService from "../../../services/coupon.service";
import './cartCard.css'

interface ICartCardProps {
    couponId: number,
    amount: number
}

const CartCard = ({ couponId, amount }: ICartCardProps) => {

    let defaultCoupon: ICoupon = {
        id: 1,
        code: 'a',
        companyId: 1,
        companyName: 'a',
        title: 'a',
        description: 'a',
        category: 'a',
        startDate: '1',
        endDate: '1',
        amount: 1,
        price: 1,
        imageUrl: 'a'

    }

    const [coupon, setCoupon] = useState<ICoupon>(defaultCoupon);

    useEffect(() => {
        CouponsService.getCouponById(couponId).then((response) => {
            setCoupon(response.data);
        });
    }, []);

    return (
        <div className="cart-card ">
            <Row>
                <Col sm={2}>
                    <div className="cart-card-price-container">
                        <p className="cart-card-coupons-amount">{amount}</p>
                    </div>
                </Col>
                <Col sm={7}>
                    <div className="cart-card-card-body">
                        <h3 className="cart-card-title">{coupon.title}</h3>
                        <h4 className="cart-card-description">{coupon.description}</h4>
                    </div>
                </Col>
                <Col sm={3}>
                    <div className="cart-card-image-container">
                        <Image src={coupon.imageUrl} />
                    </div>
                </Col>
            </Row>
        </div >
    );
}

export default CartCard;
import { useEffect, useState } from "react";
import { Col, Row, Image } from "react-bootstrap";
import { useDispatch, useSelector } from "react-redux";
import { ICoupon } from "../../../models/ICoupon";
import { IPurchaseRequest } from "../../../models/IPurchaseRequest";
import { ActionType } from "../../../redux/action-type";
import { AppState } from "../../../redux/app-state";
import CouponsService from "../../../services/coupon.service";
import './cartCard.css'

interface ICartCardProps {
    couponId: number,
    amount: number,
    onRemoveItem: (id: number) => void
}

const CartCard = ({ couponId, amount, onRemoveItem }: ICartCardProps) => {

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
    const cart: IPurchaseRequest[] = useSelector((state: AppState) => state.cart);
    const [requstedAmount, setRequestedAmount] = useState<number>(0);
    const dispatch = useDispatch();

    function changeAmount(amount: number) {
        cart.map(cartItem => {
            if (cartItem.couponId === couponId) {
                setRequestedAmount(amount);
                cartItem.amount = amount;
            }
        });
        dispatch({ type: ActionType.ADD_TO_CART, payload: { ...cart } });
        localStorage.setItem('cart', JSON.stringify(cart));
    }

    useEffect(() => {
        CouponsService.getCouponById(couponId).then((response) => {
            setCoupon(response.data);
        });
        setRequestedAmount(amount);
    }, [cart]);

    return (
        <div className="cart-card ">
            <Row>
                <Col sm={2}>
                    <div className="cart-card-price-container">
                        <div className="amount-group">
                            <input type="button" value="-" onClick={() => changeAmount(requstedAmount - 1)} disabled={requstedAmount - 1 === 0} />
                            <p className="cart-card-coupons-amount">{requstedAmount}</p>
                            <input type="button" value="+" onClick={() => changeAmount(requstedAmount + 1)} disabled={requstedAmount + 1 > coupon.amount} />
                        </div>
                        <div className="remove-item">
                            <input type="button" className="remove-item" value="remove" onClick={() => onRemoveItem(coupon.id)} />
                        </div>
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
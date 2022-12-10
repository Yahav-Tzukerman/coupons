import { Button, Col, Image, Row } from "react-bootstrap";
import "./couponCard.css";
import { useDispatch, useSelector } from "react-redux";
import { Link, useNavigate } from "react-router-dom";
import { useState } from "react";
import { AppState } from "../../../../redux/app-state";
import CountdownTimer from "../../../common/CountdownTimer/CountdownTimer";
import { ICoupon } from "../../../../models/ICoupon";
import AppModal from "../../../common/AppModal/AppModal";
import AddOrEditCoupon from "../../../common/AppForms/addOrEditCoupon";


export interface ICouponCardProps {
    data: ICoupon;
    deleteCoupon: (id: number) => void;
    buyCoupon: (id: number, amount: number) => void;
    isGrayed: boolean;
}

const CouponCard = ({
    data,
    deleteCoupon,
    buyCoupon,
    isGrayed
}: ICouponCardProps) => {

    const decodedToken = useSelector((state: AppState) => state.decodedToken);
    const [amount, setAmount] = useState<number>(1);
    const navigate = useNavigate();

    return (
        <div className={isGrayed ? "grayed-card" : "card"}>
            <Row>
                {decodedToken.roles === "ROLE_ADMIN" &&

                    <Col sm={12}>
                        <input type="button" className="btn-delete" value="X" onClick={() => deleteCoupon(data.id)} />
                    </Col>

                }
                <Col sm={12}>
                    <div className="countdown-timer">
                        <CountdownTimer targetDate={new Date(data.endDate)} />
                    </div>
                </Col>
                <Col sm={12}>
                    <div className="image-container">
                        <Image src={data.imageUrl} />
                    </div>
                </Col>
                <Col sm={12}>
                    <div className="card-badge">
                        <h6 className="card-badge-badge">{data.category}</h6>
                    </div>
                    <div className="card-badge-company">
                        <h6 className="card-badge-badge">{data.companyName}</h6>
                    </div>
                </Col>
                <Col sm={12}>
                    <div className="card-body">
                        <h5 className="card-title">{data.title}</h5>
                        {/* <h6>{data.description}</h6> */}
                    </div>
                </Col>
                <div className="price-container">
                    <Col sm={6}>
                        {(decodedToken?.roles === "ROLE_CUSTOMER") && <input type="button" value="-" className={isGrayed ? "grayed-action" : "action-button"} onClick={() => setAmount(amount - 1)} disabled={isGrayed || amount <= 1} />}
                        {(decodedToken?.roles === "ROLE_CUSTOMER") && <input id="amount" type="button" className="action-button" value={amount} />}
                        {(decodedToken?.roles === "ROLE_CUSTOMER") && <input type="button" value="+" className={isGrayed ? "grayed-action" : "action-button"} onClick={() => setAmount(amount + 1)} disabled={isGrayed || amount >= data.amount} />}

                    </Col>
                    <Col sm={6}>
                        <div className="price">
                            <h5 className="price-tag">Left: {data.amount}</h5>
                        </div>
                        <div className="price">
                            <h5 className="price-tag">price: ${amount * data.price}</h5>
                        </div>
                    </Col>
                </div>
                <Col sm={12}>
                    {(decodedToken == undefined || decodedToken?.sub == '') && <input type="button" className="buy-btn" value="To Purchase Register Now!" onClick={() => navigate('/register')} />}
                    {(decodedToken?.roles === "ROLE_CUSTOMER") && <input type="button" className={isGrayed ? "grayed-button" : "buy-btn"} value="Add to cart" onClick={() => buyCoupon(data.id, amount)} disabled={isGrayed} />}
                    {(decodedToken?.roles === "ROLE_ADMIN" || decodedToken?.companyId === data.companyId) && (
                        <AppModal
                            title={"Edit Coupon"}
                            edit={true}
                            button={"edit"}
                        >
                            <AddOrEditCoupon
                                coupon={data}
                            />
                        </AppModal>)}
                </Col >
            </Row>

        </div >
    );
};

export default CouponCard;
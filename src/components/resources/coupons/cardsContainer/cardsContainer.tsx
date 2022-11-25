import { useEffect, useState } from "react";
import { Col, Row } from "react-bootstrap";
import CouponCard from "../couponCard/couponCard";
import "./cardsContainer.css";
import { useDispatch, useSelector } from "react-redux";
import { ICoupon } from "../../../../models/ICoupon";
import { AppState } from "../../../../redux/app-state";
import useCouponsActions from "../../../../hooks/useCouponsActions/useCouponActions";
import AppPagination from "../../../common/app-pagination/appPagination";

export interface ICardsContainerProps {
    coupons: ICoupon[];
    pagination: any;
}

export default function CardsContainer({ coupons, pagination }: ICardsContainerProps) {
    
    const { onDeleteCoupon, onBuyCoupon } = useCouponsActions();

    useEffect(() => {
        console.log(coupons);
    }, [coupons]);

    return (
        <div className={"coupons__page"}>
            <Row>
                <Col sm={12}>
                    <div className="cards-container">
                        {coupons.map((coupon) => (
                            <CouponCard
                                key={`coupon-${coupon.id}`}
                                data={coupon}
                                deleteCoupon={onDeleteCoupon}
                                buyCoupon={onBuyCoupon}
                                isGrayed={false}
                            />
                        ))}
                    </div>

                </Col>
            </Row>
            <Row>
                <Col sm={12}>
                    <AppPagination
                        pagination={pagination}
                    />
                </Col>
            </Row>
        </div >
    );
}
import { useEffect, useState } from "react";
import { Col, Row } from "react-bootstrap";
import { useDispatch, useSelector } from "react-redux";
import useCoupons from "../../../hooks/useCoupons/useCoupons";
import { ActionType } from "../../../redux/action-type";
import { AppState } from "../../../redux/app-state";
import Filters from "../../common/filters-container/Filters";
import CardsContainer from "../../resources/coupons/cardsContainer/cardsContainer";
import "./couponsPage.css";


export default function CouponsPage() {
    // const appCoupons = useSelector((state: AppState) => state.coupons);
    // const decodedToken = useSelector((state: AppState) => state.decodedToken);
    // const dispatch = useDispatch();
    const {
        coupons,
        pagination,
        filters
    } = useCoupons({});


    useEffect(() => {
        pagination.setPageSize(8);
        console.log(coupons);
        
        // dispatch({ type: ActionType.AppCoupons, payload: { coupons } });
        
    }, [coupons]);

    return (
        <div className="all-coupons-page">
            <Row>
                <Col sm={2}>
                    <div className="app-coupons-filters">
                        <Filters
                            filters={filters}
                        />
                    </div>
                </Col>
                <Col sm={10}>
                    <div className="cards-container coupons">
                        <CardsContainer
                            coupons={coupons}
                            pagination={pagination}
                        />
                    </div>
                </Col>
            </Row>
        </div >
    );
}
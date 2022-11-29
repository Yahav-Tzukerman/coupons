import React, { useEffect, useState } from "react";
import { Col, Row } from "react-bootstrap";
import { useSelector } from "react-redux";
import { AppState } from "../../../../redux/app-state";
import Cart from "../../../cart/cart";
import CustomerDetails from "../../../resources/customers/CustomerDetails/customerDetails";
import '../../profilePage/profilePage.css';

interface IProps {

}

const CustomerPage: React.FC<IProps> = () => {

    const decodedToken = useSelector((state: AppState) => state.decodedToken)

    return (
        <>
            <div className="customer-page-container">
                <div className='spacer-3' />
                <h1 className="main-header">{`hello ${decodedToken.sub}`}</h1>
                <div className='spacer-3' />
                <div className="customer-container">
                    <Row>
                        <Col sm={4}>
                            <div className="customer-profile-panel">
                                <CustomerDetails />
                            </div>
                        </Col>
                        <Col sm={8}>
                            <div className="customer-cart-panel">
                                <Cart />
                            </div>
                        </Col>
                    </Row>
                </div>
                <div className='spacer-3' />
            </div>
        </>
    );
}

export default CustomerPage;
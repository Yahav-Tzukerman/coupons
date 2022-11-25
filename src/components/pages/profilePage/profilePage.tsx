import React, { useEffect, useState } from "react";
import { Col, Row } from "react-bootstrap";
import { useSelector } from "react-redux";
import usePurchases from "../../../hooks/usePurchases/usePurchases";
import { AppState } from "../../../redux/app-state";
import Cart from "../../cart/cart";
import AppPagination from "../../common/app-pagination/appPagination";
import CustomerDetails from "../../resources/customers/CustomerDetails/customerDetails";
import AdminPage from "../admin/adminPage";

import './profilePage.css';
import CompanyUserPage from '../usersPages/companyUserPage/companyUserPage';



interface IProps {

}

const ProfilePage: React.FC<IProps> = () => {

    // const { purchases, purchasesPagination } = usePurchases({});
    const decodedToken = useSelector((state: AppState) => state.decodedToken);

    if (decodedToken?.roles === "ROLE_ADMIN") {
        return <AdminPage />
    }
    if (decodedToken?.roles === "ROLE_COMPANY") {
        return <CompanyUserPage />
    }
    return (
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
    );
}

export default ProfilePage;
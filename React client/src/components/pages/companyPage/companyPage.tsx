import React, { useEffect, useState } from "react";
import { Col, Row } from "react-bootstrap";
import { useParams } from "react-router-dom";
import useCoupons from "../../../hooks/useCoupons/useCoupons";
import { ICompany } from "../../../models/ICompany";
import CompanyService from "../../../services/company.service";
import AppPagination from "../../common/app-pagination/appPagination";
import CompanyCard from "../../resources/companies/companyCard/companyCard";
import CouponCard from "../../resources/coupons/couponCard/couponCard";
import './companyPage.css'


const defaultCompany = {
    id: 0,
    name: '',
    phone: '',
    address: '',
    foundationDate: '',
    logo: ''
}

export default function CompanyPage() {
    const [company, setCompany] = useState<ICompany>(defaultCompany);
    const { coupons, pagination, filters } = useCoupons({});

    const { companyId } = useParams();
    const id = companyId === undefined ? 0 : parseInt(companyId);

    useEffect(() => {
        CompanyService.getCompany(id.toString()).then((response) => {
            filters.onDefaultFilter();
            filters.onCompanyChange(id);
            setCompany(response.data);
        });
    }, []);

    return (
        <div className="company-page">
            <Row>
                <Col sm={12}>
                    <CompanyCard
                        key={`coupon-${company.id}`}
                        data={company}
                        deleteCompany={() => { }}
                    />
                </Col>
            </Row>
            <Row>
                <Col sm={12}>
                    {coupons.length === 0 && <h1>No Coupons Here Yet...</h1>}
                    {coupons.map(coupon => (
                        <CouponCard
                            key={`coupon-${coupon.id}`}
                            data={coupon}
                            deleteCoupon={() => { }}
                            buyCoupon={() => { }}
                            isGrayed={false}
                        />
                    ))}
                </Col>
            </Row>
            <Row>
                <Col sm={12}>
                    <AppPagination
                        pagination={pagination}
                    />
                </Col>
            </Row>
        </div>
    );
}

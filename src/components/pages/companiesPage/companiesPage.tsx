import React, { useEffect, useState } from "react";
import { Col, Pagination, Row } from "react-bootstrap";
import { useDispatch, useSelector } from "react-redux";
import './companiesPage.css';
import '../../../assets/styles.css';
import { ICompany } from "../../../models/ICompany";
import { AppState } from "../../../redux/app-state";
import CompanyService from "../../../services/company.service";
import CompanyCard from "../../resources/companies/companyCard/companyCard";

export default function CompaniesPage() {
    const [companies, setCompanies] = useState<ICompany[]>([]);
    // const coupons = useSelector((state: AppState) => state.coupons);
    const [pageNumber, setPageNumber] = useState<number>(0);
    const [pageSize, setPageSize] = useState<number>(3);
    const [totalElements, setTotalElements] = useState<number>(0);
    const decodedToken = useSelector((state: AppState) => state.decodedToken);
    const dispatch = useDispatch();
    const [modalIsOpen, setIsOpen] = useState(false);

    useEffect(() => {
        CompanyService.getAllCompanies(pageNumber, pageSize).then((response) => {
            // localStorage.setItem('compaines', JSON.stringify(response.data.content));
            setCompanies(response.data.content);
            setTotalElements(response.data.totalElements);
        });
    }, [pageNumber]);

    let items = [];
    for (let i = 1; i <= Math.ceil(totalElements / pageSize); i++) {
        items.push(
            <Pagination.Item
                key={i}
                active={i === pageNumber + 1}
                onClick={() => changePage(i - 1)}
            >
                {i}
            </Pagination.Item>
        );
    }

    const changePage = (pageNumber: number) => {
        setPageNumber(pageNumber);
    };

    function onDeleteCompany(id: number) {

        // CouponsService.deleteCoupon(id).then((response) => {
        //   alert(`coupon ${id} was deleted successfully!`)
        // }).catch((error) => {
        //   alert(error.response.data.message);
        // });

        let updatedCouponsArray = companies.filter((company) => company.id !== id);
        // dispatch({ type: ActionType.Coupons, payload: updatedCouponsArray });
        setCompanies(updatedCouponsArray);
        window.location.reload();
    }

    return (
        <div className="company-page">
            <Row>
                <Col sm={12} >
                    <h1 className="main-heading">
                        Companies
                    </h1>
                    {companies.map(company => (
                        <CompanyCard
                            key={`coupon-${company.id}`}
                            data={company}
                            deleteCompany={() => onDeleteCompany(company.id)}
                        />
                    ))}
                </Col>
            </Row>
            <Row>
                <Col sm={12} >
                    <Pagination className="pagination">
                        {totalElements > pageSize && items}
                    </Pagination>
                </Col>
            </Row>
        </div>
    );
}

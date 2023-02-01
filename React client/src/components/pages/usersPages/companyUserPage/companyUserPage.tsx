import React, { useEffect, useState } from "react";
import { Col, Row } from "react-bootstrap";
import { useSelector } from "react-redux";
import useCoupons from "../../../../hooks/useCoupons/useCoupons";
import useCouponsActions from "../../../../hooks/useCouponsActions/useCouponActions";
import { AppState } from "../../../../redux/app-state";
import AppPagination from "../../../common/app-pagination/appPagination";
import AddOrEditCoupon from "../../../common/AppForms/addOrEditCoupon";
import AppModal from "../../../common/AppModal/AppModal";

interface IProps {

}

const CompanyUserPage: React.FC<IProps> = () => {

    const decodedToken = useSelector((state: AppState) => state.decodedToken);
    const [couponsIsOpen, setcouponsIsOpen] = useState(false);
    const [statistics, setStatistics] = useState(false);
    const { coupons, filters, pagination } = useCoupons({});
    const { onDeleteCoupon } = useCouponsActions();

    const handlePressCoupons = () => setcouponsIsOpen(!couponsIsOpen);
    const handlePressStatistics = () => setStatistics(!statistics);

    useEffect(() => {
        filters.onCompanyChange(decodedToken.companyId ? decodedToken.companyId : 0);
    }, [coupons]);

    return (
        <>
            <div className="admin-page-container">
                <div className='spacer-3' />
                <h1 className="main-heading">Company Admin Page</h1>
                <div className='spacer-3' />
                <input type="button" value="Coupons" className="accordion" onClick={handlePressCoupons} />
                <div className={couponsIsOpen ? "" : "closed-panel"}>
                    <div className="inside-container">
                        <Row>
                            <Col sm={12}>
                                <AppModal
                                    title={"Add Coupon"}
                                >
                                    <AddOrEditCoupon />
                                </AppModal>
                            </Col>
                        </Row>
                        <Row>
                            <Col sm={12}>
                                <table className="table">
                                    <thead>
                                        <tr>
                                            <th scope="col">Id</th>
                                            <th scope="col">CompanyId</th>
                                            <th scope="col">Category</th>
                                            <th scope="col">Title</th>
                                            <th scope="col">Description</th>
                                            <th scope="col">StartDate</th>
                                            <th scope="col">EndDate</th>
                                            <th scope="col">Amount</th>
                                            <th scope="col">Price</th>
                                            <th scope="col">Image</th>
                                            <th scope="col">Actions</th>
                                        </tr>
                                    </thead>
                                    {coupons.map(coupon => (
                                        <tbody key={coupon.id}>
                                            <tr>
                                                <th scope="row">{coupon.id}</th>
                                                <td>{coupon.companyId}</td>
                                                <td>{coupon.category}</td>
                                                <td>{coupon.title}</td>
                                                <td id="description">{coupon.description}</td>
                                                <td>{coupon.startDate}</td>
                                                <td>{coupon.endDate}</td>
                                                <td>{coupon.amount}</td>
                                                <td>{coupon.price}</td>
                                                <td id="image">{coupon.imageUrl}</td>
                                                <td>
                                                    <input type="button" className="material-symbols-outlined" value="delete" data-tip="delete coupon" data-place="left" onClick={() => onDeleteCoupon(coupon.id)} />

                                                    <AppModal
                                                        title={"Edit Coupon"}
                                                        edit={true}
                                                    >
                                                        <AddOrEditCoupon
                                                            coupon={coupon}
                                                        />
                                                    </AppModal>
                                                </td>
                                            </tr>
                                        </tbody>
                                    ))}
                                </table>
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
                </div>

                <input type="button" value="Statistics" className="accordion" onClick={handlePressStatistics} />
                <div className={statistics ? "" : "closed-panel"}>
                    <div className="inside-container">
                        <Row>
                            <Col sm={3}>
                                <div className="statistics-total-users">
                                    <span id="statistics-total-users-icon" className="material-symbols-outlined">
                                        person
                                    </span>
                                    <div className="statistics-total-users-data">
                                        <h4>Statistics</h4>
                                    </div>
                                </div>
                            </Col>
                            <Col sm={3}>
                                <div className="statistics-total-users">
                                    <span id="statistics-total-users-icon" className="material-symbols-outlined">
                                        apartment
                                    </span>
                                    <div className="statistics-total-users-data">
                                    </div>
                                </div>
                            </Col>
                            <Col sm={3}>
                                <div className="statistics-total-users">
                                    <span id="statistics-total-users-icon" className="material-symbols-outlined">
                                        style
                                    </span>
                                    <div className="statistics-total-users-data">
                                        <h4>Coupons</h4>
                                        <h4>{pagination.totalElements}</h4>
                                    </div>
                                </div>
                            </Col>
                            <Col sm={3}>
                                <div className="statistics-total-users">
                                    <span id="statistics-total-users-icon" className="material-symbols-outlined">
                                        style
                                    </span>
                                    <div className="statistics-total-users-data">
                                    </div>
                                </div>
                            </Col>
                        </Row>
                    </div>
                </div>
                <div className='spacer-3' />
            </div >
        </>
    );
}

export default CompanyUserPage;
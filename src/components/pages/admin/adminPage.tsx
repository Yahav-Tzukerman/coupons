import React, { useEffect, useState } from "react";
import { Col, Row } from "react-bootstrap";
import useCoupons from "../../../hooks/useCoupons/useCoupons";
import useCouponsActions from "../../../hooks/useCouponsActions/useCouponActions";
import useUsers from "../../../hooks/useUsers/useUsers";
import useCategories from "../../../hooks/useCategories/useCategories";
import useCompanies from "../../../hooks/useCompanies/useCompanies";
import usePurchases from "../../../hooks/usePurchases/usePurchases";
import AppModal from "../../common/AppModal/AppModal";
import AddOrEditCoupon from "../../common/AppForms/addOrEditCoupon";
import AppPagination from "../../common/app-pagination/appPagination";
import SalesPerMonth from "../../Statistics/TotalSalesPerMonthChart/SalesPerMonth";
import AddOrEditUser from "../../common/AppForms/addOrEditUser";
import AddOrEditCompany from "../../common/AppForms/addOrEditCompany";
import './adminPage.css';

interface IProps {

}

const AdminPage: React.FC<IProps> = () => {

    const [couponsIsOpen, setcouponsIsOpen] = useState(false);
    const [categosriesIsOpen, setCategoriesIsOpen] = useState(false);
    const [usersIsOpen, setusersIsOpen] = useState(false);
    const [companiesIsOpen, setCompaniesIsOpen] = useState(false);
    const [statistics, setStatistics] = useState(false);

    const { coupons, pagination } = useCoupons({});
    const { onDeleteCoupon } = useCouponsActions();
    const { users, usersPagination, onDeleteUser } = useUsers();
    const { categories, categoriesPagination, onDeleteCategory } = useCategories();
    const { companies, companiesPagination, onDeleteCompany } = useCompanies();
    const { purchases, purchasesPagination, total } = usePurchases({});


    const handlePressCoupons = () => setcouponsIsOpen(!couponsIsOpen);
    const handlePressCategories = () => setCategoriesIsOpen(!categosriesIsOpen);
    const handlePressUsers = () => setusersIsOpen(!usersIsOpen);
    const handlePressCompanies = () => setCompaniesIsOpen(!companiesIsOpen);
    const handlePressStatistics = () => setStatistics(!statistics);

    return (
        <>
            <div className="admin-page-container">
                <div className='spacer-3' />
                <h1 className="main-header">Admin Page</h1>
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
                                {/* <input id="add" type="button" className="material-symbols-outlined" value="add" data-tip="add new coupon" data-place="left" /> */}
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
                                                    <input type="button" className="material-symbols-outlined" value="delete" data-tip="delete coupon" data-place="left" onClick={() => { }} />

                                                    <AppModal
                                                        title={"Edit Coupon"}
                                                        edit={true}
                                                    >
                                                        <AddOrEditCoupon 
                                                            coupon={coupon}
                                                            />
                                                    </AppModal>
                                                    {/* <input type="button" className="material-symbols-outlined" value="edit" data-tip="delete coupon" data-place="left" /> */}
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
                                        <h4>Users</h4>
                                        <h4>{usersPagination.totalElements}</h4>
                                    </div>
                                </div>
                            </Col>
                            <Col sm={3}>
                                <div className="statistics-total-users">
                                    <span id="statistics-total-users-icon" className="material-symbols-outlined">
                                        apartment
                                    </span>
                                    <div className="statistics-total-users-data">
                                        <h4>Companies</h4>
                                        <h4>{companiesPagination.totalElements}</h4>
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
                                        <h4>Total Revenue</h4>
                                        <h4>{total}</h4>
                                    </div>
                                </div>
                            </Col>
                        </Row>
                        <Row>
                            <Col sm={12}>
                                <div className="chart-container">
                                    <SalesPerMonth />
                                </div>
                            </Col>
                        </Row>
                        <Row>
                            <Col sm={12}>
                                <div className="chart-map">
                                    <div className="chart-map-max">
                                        <h4>Highest income</h4>
                                    </div>
                                    <div className="chart-map-second-max">
                                        <h4>Second highest income</h4>
                                    </div>
                                </div>
                            </Col>
                        </Row>
                    </div>
                </div>

                <input type="button" value="Users" className="accordion" onClick={handlePressUsers} />
                <div className={usersIsOpen ? "" : "closed-panel"}>
                    <div className="inside-container">
                        <Row>
                            <Col sm={12}>
                                <AppModal
                                    title={"Edit User"}
                                >
                                    <AddOrEditUser />
                                </AppModal>
                                {/* <input id="add" type="button" className="material-symbols-outlined" value="add" data-tip="add new user" data-place="left" /> */}
                            </Col>
                        </Row>
                        <Row>
                            <Col sm={12}>
                                <table className="table">
                                    <thead>
                                        <tr>
                                            <th scope="col">Id</th>
                                            <th scope="col">FirstName</th>
                                            <th scope="col">LastName</th>
                                            <th scope="col">Username</th>
                                            <th scope="col">UserType</th>
                                            <th scope="col">CompanyId</th>
                                            <th scope="col">Actions</th>
                                        </tr>
                                    </thead>
                                    {users.map(user => (
                                        <tbody key={user.id}>
                                            <tr>
                                                <th scope="row">{user.id}</th>
                                                <td>{user.firstName}</td>
                                                <td>{user.lastName}</td>
                                                <td>{user.username}</td>
                                                <td>{user.role}</td>
                                                <td>{user.companyId}</td>
                                                <td>
                                                    <input type="button" className="material-symbols-outlined" value="delete" data-tip="delete user" data-place="left" onClick={() => { }} />
                                                    <AppModal
                                                        title={"Edit User"}
                                                    >
                                                        <AddOrEditUser user={user} />
                                                    </AppModal>
                                                    {/* <input type="button" className="material-symbols-outlined" value="edit" data-tip="edit user" data-place="left" /> */}
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
                                    pagination={usersPagination}
                                />
                            </Col>
                        </Row>
                    </div>
                </div>

                <input type="button" value="Companies" className="accordion" onClick={handlePressCompanies} />
                <div className={companiesIsOpen ? "" : "closed-panel"}>
                    <div className="inside-container">
                        <Row>
                            <Col sm={12}>
                                <AppModal
                                    title={"Edit Company"}
                                >
                                    <AddOrEditCompany />
                                </AppModal>
                                {/* <input id="add" type="button" className="material-symbols-outlined" value="add" data-tip="add new user" data-place="left" /> */}
                            </Col>
                        </Row>
                        <Row>
                            <Col sm={12}>
                                <table className="table">
                                    <thead>
                                        <tr>
                                            <th scope="col">Id</th>
                                            <th scope="col">address</th>
                                            <th scope="col">name</th>
                                            <th scope="col">phoneNumber</th>
                                            <th scope="col">companyImage</th>
                                            <th scope="col">Actions</th>
                                        </tr>
                                    </thead>
                                    {companies.map(company => (
                                        <tbody key={company.id}>
                                            <tr>
                                                <th scope="row">{company.id}</th>
                                                <td>{company.address}</td>
                                                <td>{company.name}</td>
                                                <td>{company.phone}</td>
                                                <td>{company.logo}</td>
                                                <td>
                                                    <input type="button" className="material-symbols-outlined" value="delete" data-tip="delete user" data-place="left" onClick={() => { }} />
                                                    <AppModal
                                                        title={"Edit Company"}
                                                    >
                                                        <AddOrEditCompany company={company} />
                                                    </AppModal>
                                                    {/* <input type="button" className="material-symbols-outlined" value="edit" data-tip="edit user" data-place="left" /> */}
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
                                    pagination={companiesPagination}
                                />
                            </Col>
                        </Row>
                    </div>
                </div>
                <div className='spacer-3' />
            </div >
        </>

    );

}

export default AdminPage;
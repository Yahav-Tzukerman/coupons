import { useEffect, useState } from "react";
import { Col, Row } from "react-bootstrap";
import { useSelector } from "react-redux";
import { ICustomerResponse } from "../../../../models/ICustomerResponse";
import { IUserResponse } from '../../../../models/IUserResponse';
import { AppState } from "../../../../redux/app-state";
import CustomerService from "../../../../services/customer.service";
import UserService from '../../../../services/users.service';
import '../../../pages/profilePage/profilePage.css'




const CustomerDetails = () => {

    const defaultUser: IUserResponse = {
        id: 0,
        username: "",
        firstName: "",
        lastName: "",
        role: "COMPANY",
        phone: "",
        companyId: 0,
        joinDate: new Date(),
        token: ''
    };

    let defaultCustomer: ICustomerResponse = {
        id: 0,
        address: "",
        amountOfChildren: 0,
        birthDate: "",
        gender: "",
        maritalStatus: ""
    }

    const decodedToken = useSelector((state: AppState) => state.decodedToken);
    const [customer, setCustomer] = useState<ICustomerResponse>(defaultCustomer);
    const [user, setUser] = useState<IUserResponse>(defaultUser);

    useEffect(() => {
        CustomerService.getCustomerById(decodedToken?.userId).then((response) => {
            setCustomer(response.data);
        });
        UserService.getUserById(decodedToken?.userId).then(response => {
            setUser(response.data);
        });
    }, []);

    return (

        <Row>
            <Col sm={12}>
                <div className="profile-input-container">
                    <div className="text-container">
                        <h5 className="customer-page-profile">
                            first name :
                        </h5>
                    </div>
                    <div className="user-details">
                        {user.firstName}
                    </div>
                </div>
                <div className="profile-input-container">
                    <div className="text-container">
                        <h5 className="customer-page-profile">
                            last name :
                        </h5>
                    </div>
                    <div className="user-details">
                        {user.lastName}
                    </div>
                </div>
                <div className="profile-input-container">
                    <div className="text-container">
                        <h5 className="customer-page-profile">
                            user name :
                        </h5>
                    </div>
                    <div className="user-details">
                        {user.username}
                    </div>
                </div>
                <div className="profile-input-container">
                    <div className="text-container">
                        <h5 className="customer-page-profile">
                            address :
                        </h5>
                    </div>
                    <div className="user-details">
                        {customer.address}
                    </div>
                </div>
                <div className="profile-input-container">
                    <div className="text-container">
                        <h5 className="customer-page-profile">
                            phone:
                        </h5>
                    </div>
                    <div className="user-details">
                        {user.phone}
                    </div>
                </div>
            </Col>
        </Row>
    );
};

export default CustomerDetails;
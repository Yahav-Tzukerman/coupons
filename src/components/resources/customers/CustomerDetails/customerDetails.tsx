import { useEffect, useState } from "react";
import { Col, Row } from "react-bootstrap";
import { useSelector } from "react-redux";
import useUsers from "../../../../hooks/useUsers/useUsers";
import { ICustomerResponse } from "../../../../models/ICustomerResponse";
import { IUserResponse } from '../../../../models/IUserResponse';
import { AppState } from "../../../../redux/app-state";
import CustomerService from "../../../../services/customer.service";
import UserService from '../../../../services/users.service';




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
    const [editFirstName, setEditFirstName] = useState<boolean>(true);
    const [editLastName, setEditLastName] = useState<boolean>(true);
    const [editUserName, setEditUserName] = useState<boolean>(true);
    const [editAddress, setEditAddress] = useState<boolean>(true);
    const [editPhoneNumber, setEditPhoneNumber] = useState<boolean>(true);

    const [message, setMessage] = useState('');

    const [updated, setUpdated] = useState(message);

    const handleChange = (event: any) => {
        setMessage(event.target.value);
    };

    const handleClick = () => {
        setUpdated(message);
    };

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
                    <input type="button" id="re" className="material-symbols-outlined" value="edit" data-tip="delete coupon" data-place="left" onClick={() => setEditFirstName(!editFirstName)} />
                    <div className="text-container">
                        <h5 className="customer-page-profile">
                            first name :
                        </h5>
                    </div>
                    <input
                        type="text"
                        id="message"
                        name="message"
                        defaultValue={user?.firstName}
                        placeholder={user?.firstName}
                        onChange={handleChange}
                        disabled={editFirstName}
                    />
                    <input type={editFirstName ? "hidden" : "button"} className="profile-edit-btn" value="Update" onClick={handleClick} />
                </div>
                <div className="profile-input-container">
                    <input type="button" id="re" className="material-symbols-outlined" value="edit" data-tip="delete coupon" data-place="left" onClick={() => setEditLastName(!editLastName)} />
                    <div className="text-container">
                        <h5 className="customer-page-profile">
                            last name :
                        </h5>
                    </div>
                    <input
                        type="text"
                        id="message"
                        name="message"
                        defaultValue={user?.lastName}
                        placeholder={user?.lastName}
                        onChange={handleChange}
                        disabled={editLastName}
                    />
                    <input type={editLastName ? "hidden" : "button"} className="profile-edit-btn" value="Update" onClick={handleClick} />
                </div>
                <div className="profile-input-container">
                    <input type="button" id="re" className="material-symbols-outlined" value="edit" data-tip="delete coupon" data-place="left" onClick={() => setEditUserName(!editUserName)} />
                    <div className="text-container">
                        <h5 className="customer-page-profile">
                            user name :
                        </h5>
                    </div>
                    <input
                        type="text"
                        id="message"
                        name="message"
                        defaultValue={user?.username}
                        placeholder={user?.username}
                        onChange={handleChange}
                        disabled={editUserName}
                    />
                    <input type={editUserName ? "hidden" : "button"} className="profile-edit-btn" value="Update" onClick={handleClick} />
                </div>
                <div className="profile-input-container">
                    <input type="button" id="re" className="material-symbols-outlined" value="edit" data-tip="delete coupon" data-place="left" onClick={() => setEditAddress(!editAddress)} />
                    <div className="text-container">
                        <h5 className="customer-page-profile">
                            address :
                        </h5>
                    </div>
                    <input
                        type="text"
                        id="message"
                        name="message"
                        defaultValue={customer.address}
                        placeholder={customer.address}
                        onChange={handleChange}
                        disabled={editAddress}
                    />
                    <input type={editAddress ? "hidden" : "button"} className="profile-edit-btn" value="Update" onClick={handleClick} />
                </div>
                <div className="profile-input-container">
                    <input type="button" id="re" className="material-symbols-outlined" value="edit" data-tip="delete coupon" data-place="left" onClick={() => setEditPhoneNumber(!editPhoneNumber)} />
                    <div className="text-container">
                        <h5 className="customer-page-profile">
                            phone:
                        </h5>
                    </div>
                    <input
                        type="text"
                        id="message"
                        name="message"
                        defaultValue={user?.phone}
                        placeholder={user?.phone}
                        onChange={handleChange}
                        disabled={editPhoneNumber}
                    />
                    <input type={editPhoneNumber ? "hidden" : "button"} className="profile-edit-btn" value="Update" onClick={handleClick} />
                </div>
            </Col>
        </Row>
    );
};

export default CustomerDetails;
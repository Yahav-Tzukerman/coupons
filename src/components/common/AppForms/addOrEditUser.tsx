import React, { useEffect, useState } from "react"
import { ICompanyList } from "../../../models/ICompanyList";
import { IRequestUser } from "../../../models/IRequestUser";
import { IUserResponse } from "../../../models/IUserResponse";
import UserService from "../../../services/users.service";
import CompaniesDropDown from "../../resources/companies/companiesDropDown/companiesDropDown";
import './Form.css';


export interface IAddOrEditUserProps {
    user?: IUserResponse;
}

const AddOrEditUser = ({ user }: IAddOrEditUserProps) => {

    const defaultUser: IRequestUser = {
        id: 0,
        username: "",
        password: "",
        firstName: "",
        lastName: "",
        role: "",
        phone: "",
        companyId: null
    }

    // const coupons = useSelector((state: AppState) => state.coupons);
    // const [user, setUser] = useState<IRequestUser>(defaultUser);
    const [username, setUsername] = useState<string>("");
    const [password, setPassword] = useState<string>("");
    const [firstName, setFirstName] = useState<string>("");
    const [lastName, setLastName] = useState<string>("");
    const [role, setRole] = useState<string>("");
    const [phone, setPhone] = useState<string>("");
    const [companyId, setCompanyId] = useState<number>(0);
    const [companyList, setCompanyList] = useState<ICompanyList>();
    const [error, setError] = useState<string>("");


    useEffect(() => {
        load();
    }, []);

    const load = () => {
        if (user?.id) {
            setUsername(user.username);
            setFirstName(user.firstName);
            setLastName(user.lastName);
            setCompanyId(user?.companyId === undefined ? 0 : user.companyId);
            setPhone(user.phone);
            setRole(user.role);
        }
    }

    const initDefaultCoupon = () => {
        defaultUser.id = user?.id === undefined ? 0 : user.id;
        defaultUser.username = username;
        defaultUser.password = password;
        defaultUser.firstName = firstName;
        defaultUser.lastName = lastName;
        defaultUser.phone = phone;
        defaultUser.role = role;
    }


    const onEditUser = () => {
        initDefaultCoupon();
        console.log(JSON.stringify(defaultUser));


        UserService.updateUser(defaultUser).then(response => {
            localStorage.setItem('updatedCouponn', JSON.stringify(response.data));
            window.location.reload();
        }).catch(error => {
            setError(error.response.data.message);
            localStorage.setItem('addError', error);
        });
    }

    const onAddUser = () => {
        initDefaultCoupon();

        localStorage.setItem('addeddCoupon', JSON.stringify(defaultUser));

        UserService.addUser(defaultUser).then(response => {
            localStorage.setItem('addedCouponn', JSON.stringify(response.data));
            window.location.reload();
        }).catch(error => {
            setError(error.response.data.message);
            localStorage.setItem('addError', error);
        });
    }

    return (
        <div className="add-coupon-container">
            <h1 className="add-coupon-header">{user?.id ? "EDIT" : "ADD"} Users</h1>
            <form>
                <div className="add-coupon-form">
                    <div className="modal-form__group">
                        <input
                            type="text"
                            placeholder={user?.username ? user.username : "Enter Username"}
                            name="username"
                            id="username"
                            className="add-coupon-input"
                            onChange={(e) => setUsername(e.target.value)}
                            required
                        />
                    </div>
                    <div className="modal-form__group">
                        <input
                            type="text"
                            placeholder={"Enter New Password"}
                            name="password"
                            id="password"
                            className="add-coupon-input"
                            onChange={(e) => setPassword(e.target.value)}
                            required
                        />
                    </div>
                    <div className="modal-form__group">
                        <input
                            type="text"
                            placeholder={user?.firstName ? user.firstName : "Enter firstName"}
                            name="firstName"
                            id="firstName"
                            className="add-coupon-input"
                            onChange={(e) => setFirstName(e.target.value)}
                            required
                        />
                    </div>
                    <div className="modal-form__group">
                        <input
                            type="text"
                            placeholder={user?.lastName ? user.lastName : "Enter Last Name"}
                            name="lastName"
                            id="lastName"
                            className="add-coupon-input"
                            onChange={(e) => setLastName(e.target.value)}
                            required
                        />
                    </div>
                    <div className="modal-form__group">
                        <input
                            type="text"
                            placeholder={user?.phone ? user.phone : "Enter phobe"}
                            name="phone"
                            id="phone"
                            className="add-coupon-input"
                            onChange={(e) => setPhone(e.target.value)}
                            required
                        />
                    </div>
                    <div className="modal-form__group">
                        <input
                            type="text"
                            placeholder={user?.role ? user.role : "Enter Role"}
                            name="role"
                            id="role"
                            className="add-coupon-input"
                            onChange={(e) => setRole(e.target.value)}
                            required
                        />
                    </div>

                    <CompaniesDropDown
                        selectedCompany={setCompanyId}
                        defaultCompanyId={user?.companyId === undefined ? 0 : user.companyId} />

                    {error !== "" && <div className="error">{error}</div>}
                    <div className="">
                        {!user?.id && <input type="button" className="add-coupon-button" value="add coupon" onClick={onAddUser} />}
                        {user?.id && <input type="button" className="add-coupon-button" value="edit coupon" onClick={onEditUser} />}
                    </div>
                </div>
            </form>
        </div>
    );
};

export default AddOrEditUser;
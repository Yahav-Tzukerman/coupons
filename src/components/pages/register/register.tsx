import { ChangeEvent, useState } from "react";
import {
    Card,
    ListGroup,
} from "react-bootstrap";
import { useSelector } from "react-redux";
import './register.css'
import { useNavigate } from "react-router-dom";
import { IRequestUser } from "../../../models/IRequestUser";
import { ICustomerRequest } from "../../../models/ICustomerRequest";
import CustomerService from "../../../services/customer.service";
import { AppState } from "../../../redux/app-state";



const { Header, Body, Footer, Title, Text } = Card;
const { Item } = ListGroup;

export default function Register() {
    const defaultUser: IRequestUser = {
        username: "",
        password: "",
        firstName: "",
        lastName: "",
        role: "COMPANY",
        phone: "",
        companyId: 0,
    };
    const defaultCustomer: ICustomerRequest = {
        user: defaultUser,
        address: "",
        amountOfChildren: 0,
        birthDate: "",
        gender: "",
        maritalStatus: ""
    };
    const [user, setUser] = useState<IRequestUser>(defaultUser);
    const currentUser = useSelector((state: AppState) => state.currentUser);
    const [username, setUsername] = useState<string>("");
    const [password, setPassword] = useState<string>("");
    const [repeatPassword, setRepeatPassword] = useState<string>("");
    const [firstName, setFirstName] = useState<string>("");
    const [lastName, setLastName] = useState<string>("");
    const [phone, setPhone] = useState<string>("");
    const [address, setAddress] = useState<string>("");
    const [amountOfChildren, setAmountOfChildren] = useState<string>("");
    const [birthDate, setBirthDate] = useState<Date>(new Date());
    const [gender, setGender] = useState<string>("");
    const [maritalStatus, setMaritalStatus] = useState<string>("");
    const [role, setRole] = useState<string>("CUSTOMER");
    const [companyId, setCompanyId] = useState<number>(0);
    const [error, setError] = useState<string>("");
    const navigate = useNavigate();


    const onSubmit = () => {
        // let updatedUser: IRequestUser = { ...user };
        defaultUser.username = username;
        defaultUser.firstName = firstName;
        defaultUser.lastName = lastName;
        defaultUser.phone = phone;
        defaultUser.password = password;
        defaultUser.role = role;
        defaultUser.companyId = null;
        defaultCustomer.user = defaultUser;
        defaultCustomer.address = address;
        defaultCustomer.amountOfChildren = parseInt(amountOfChildren);
        defaultCustomer.birthDate = birthDate.toJSON();
        defaultCustomer.gender = gender;
        defaultCustomer.maritalStatus = maritalStatus;
        localStorage.setItem('currentUser', JSON.stringify(defaultCustomer));

        CustomerService.register(defaultCustomer).then(response => {
            console.log(response);
            localStorage.setItem('customer', JSON.stringify(response.data));
            navigate('/login');
        }).catch(error => {
            localStorage.setItem('error', JSON.stringify(error.response.data));
            alert(error.response.data.message);
        })
    }


    return (
        <div className="register_page">
            <div className="register__container">
                <h1 className="register-heading">Register Now</h1>
                <form className="register-form33">

                    {/* <div className="form__group field">
                        <input type="input" className="form__field" placeholder="Name" name="name" id='name' required />
                        <label className="form__label">Name</label>
                    </div> */}
                    <div className="form__group field">
                        <input type="email" className="form__field" placeholder="Email" onChange={(event) => { setUsername(event.target.value) }} required />
                    </div>
                    <div className="form__group field">
                        <input type="password" className="form__field" placeholder="Password" onChange={(event) => { setPassword(event.target.value) }} required />
                    </div>
                    <div className="form__group field">
                        <input type="password" className="form__field" placeholder="Repeat Password" onChange={(event) => { setRepeatPassword(event.target.value) }} required />
                    </div>

                    <div className="form__group field">
                        <input type="text" className="form__field" placeholder="First name" onChange={(event) => { setFirstName(event.target.value) }} required />
                    </div>
                    <div className="form__group field">
                        <input type="text" className="form__field" placeholder="Last name" onChange={(event) => { setLastName(event.target.value) }} required />
                    </div>

                    <div className="form__group field">
                        <input type="text" className="form__field" placeholder="Phone" onChange={(event) => { setPhone(event.target.value) }} required />
                    </div>
                    <div className="form__group field">
                        <input type="text" className="form__field" placeholder="address" onChange={(event) => { setAddress(event.target.value) }} required />
                    </div>
                    <div className="form__group field">
                        <input type="number" className="form__field" placeholder="amountOfChildren" onChange={(event) => { setAmountOfChildren(event.target.value) }} required />
                    </div>
                    <div className="form__group field">
                        <input type="date" className="form__field" placeholder="birthdate" onChange={(event) => { setBirthDate(new Date(event.target.value)) }} required />
                    </div>
                    <div className="gender">
                        <h1>Gender</h1>
                        <input type="radio" value="MALE" id="male" name="gender" onChange={e => setGender(e.target.value)} />
                        <label className="gender__label">Male</label>
                        <input type="radio" value="FEMALE" id="female" name="gender" onChange={e => setGender(e.target.value)} />
                        <label className="gender__label">Female</label>
                    </div>
                    <div className="gender">
                        <h1>Marital Status</h1>
                        <input type="radio" value="SINGLE" id="single" name="marital_status" onChange={e => setMaritalStatus(e.target.value)} />
                        <label className="gender__label">Single</label>
                        <input type="radio" value="MARRIED" id="married" name="marital_status" onChange={e => setMaritalStatus(e.target.value)} />
                        <label className="gender__label">Married</label>
                    </div>
                    <input type="button" className="register__submit" value="Register" onClick={onSubmit} />
                </form>
                <div className="screen__background">
                    <span className="screen__background__shape screen__background__shape3"></span>
                    <span className="screen__background__shape screen__background__shape2"></span>
                    <span className="screen__background__shape screen__background__shape1"></span>
                </div>
            </div>
        </div>
    );
}

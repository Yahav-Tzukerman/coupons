import { useState } from "react";
import jwt from 'jwt-decode'
import "./login.css";
import axios from "axios";
import { useDispatch, useSelector } from "react-redux";
import { Link, Navigate } from "react-router-dom";
import { useNavigate } from 'react-router-dom';
import { AppState } from "../../../redux/app-state";
import UserService from "../../../services/users.service";
import { ActionType } from "../../../redux/action-type";


export default function Login() {

    const [username, setUsername] = useState<string>("");
    const [password, setPassword] = useState<string>("");
    const [error, setError] = useState<string>("");
    const currentUser = useSelector((state: AppState) => state.currentUser);
    const dispatch = useDispatch();
    const navigate = useNavigate();

    const onLogin = () => {
        const response = UserService.login(username, password)
            .then((response) => {
                let user = response.data;
                let token = "Bearer " + user.token;
                let decodedToken = jwt(user.token);
                localStorage.setItem('decodedToken', JSON.stringify(decodedToken));
                // localStorage.setItem('decoded', JSON.stringify(decodedToken));
                dispatch({ type: ActionType.OnLogin, payload: { ...user, decodedToken } });
                axios.defaults.headers.common['Authorization'] = token;
                localStorage.setItem("currentUser", JSON.stringify(user));
                localStorage.setItem("token", user.token);
                navigate('/home')
            })
            .catch((error) => {
                // alert(error.response.data.message);
                setError(error.response.data.message);
                localStorage.setItem("error", JSON.stringify(error.response.data.message));
            });
    };

    return (
        <div className="login__page">
            <div className="container-login">
                <div className="screen-login">
                    <div className="screen__content-login">
                        <form className="login-login">
                            <div className="login__field-login">
                                <i className="login__icon fas fa-user"></i>
                                <input onChange={(event) => setUsername(event.target.value)} type="text" className="login__input" placeholder="User name / Email" required />
                            </div>
                            <div className="login__field-login">
                                <i className="login__icon fas fa-lock"></i>
                                <input onChange={(event) => setPassword(event.target.value)} type="password" className="login__input" placeholder="Password" required />
                            </div>
                            <Link to={currentUser?.token !== "" ? '/' : '/login'}>
                                <input type="button" className="button login__submit" value="Log In Now" onClick={onLogin} />
                            </Link>
                        </form>
                        <div className="login-register-navigator-header">
                            <h3>New here?</h3>
                            <div className="login-register-navigator">
                                <Link to={"/register"}>Create Account</Link>
                            </div>
                        </div>
                    </div>
                    <div className="screen__background">
                        <span className="screen__background__shape_login screen__background__shape3_login"></span>
                        <span className="screen__background__shape_login screen__background__shape2_login"></span>
                        <span className="screen__background__shape_login screen__background__shape1_login"></span>
                    </div>
                </div>
            </div>
        </div>
    );
}
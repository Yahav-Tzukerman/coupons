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
import { Col, Row } from "react-bootstrap";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faFacebook, faTwitter, faInstagram, faGoogle } from '@fortawesome/free-brands-svg-icons'
import { EMAIL_REGEX, PASSWORD_REGEX } from "../../../constants/matcher";


export default function Login() {

    const [username, setUsername] = useState<string>("");
    const [password, setPassword] = useState<string>("");
    const [error, setError] = useState<string>("");
    const decodedToken = useSelector((state: AppState) => state.decodedToken);
    const dispatch = useDispatch();
    const navigate = useNavigate();

    const onLogin = () => {
        const response = UserService.login(username, password)
            .then((response) => {
                let user = response.data;
                let token = "Bearer " + user.token;
                let decodedToken = jwt(user.token);
                localStorage.setItem('decodedToken', JSON.stringify(decodedToken));
                dispatch({ type: ActionType.OnLogin, payload: { ...user, decodedToken } });
                axios.defaults.headers.common['Authorization'] = token;
                localStorage.setItem("token", user.token);
                navigate('/coupons')
            })
            .catch((error) => {
                setError(error.response.data.message);
                localStorage.setItem("error", JSON.stringify(error.response.data.message));
            });
    };

    return (
        <div className="login__page">
            <div className="container-login">
                <div className="screen-login">
                    <Row>
                        <Col sm={8}>
                            <div className="screen__content-login">
                                <form className="login-login">
                                    <div className="login__field-login">
                                        <i className="login__icon fas fa-user"></i>
                                        <input
                                            onChange={(event) => setUsername(event.target.value)}
                                            type="text"
                                            className="login__input"
                                            placeholder="User name / Email"
                                            required
                                            onFocus={(e) => {
                                                e.target.value = `${username}`;
                                                e.target.id = "";
                                            }}
                                            onBlur={(e) => {
                                                if (!EMAIL_REGEX.exec(e.target.value)) {
                                                    e.target.value = "username must be an email"
                                                    e.target.id = "input-error";
                                                }
                                            }}
                                        />
                                    </div>
                                    <div className="login__field-login">
                                        <i className="login__icon fas fa-lock"></i>
                                        <input
                                            onChange={(event) => setPassword(event.target.value)}
                                            type="password"
                                            className="login__input"
                                            placeholder="Password"
                                            required
                                            onFocus={(e) => {
                                                e.target.type = "password"
                                                e.target.value = `${password}`;
                                                e.target.id = "";
                                            }}
                                            onBlur={(e) => {
                                                if (!PASSWORD_REGEX.exec(e.target.value)) {
                                                    e.target.type = "text"
                                                    e.target.value = "invalid password"
                                                    e.target.id = "input-error";
                                                }
                                            }} />
                                    </div>
                                    <Link to={decodedToken?.userId !== 0 ? '/' : '/login'}>
                                        <input type="button" className="button login__submit" value="Log In Now" onClick={onLogin} />
                                    </Link>
                                </form>
                                <div className="login-register-navigator-header">
                                    <h3>New here?</h3>
                                    <div className="login-register-navigator">
                                        <Link to={"/register"}>Create Account</Link>
                                    </div>
                                    {error !== "" && <div className="form-error">{error}</div>}
                                </div>
                            </div>
                            <div className="screen__background">
                                <span className="screen__background__shape_login screen__background__shape3_login"></span>
                                <span className="screen__background__shape_login screen__background__shape2_login"></span>
                                <span className="screen__background__shape_login screen__background__shape1_login"></span>
                            </div>
                        </Col>
                        <Col sm={4}>
                            <div className="social-login">
                                <h3>log in via</h3>
                                <div className="social-icons">
                                    <a className="social-login__icon fab fa-instagram">
                                        <FontAwesomeIcon icon={faInstagram} />
                                    </a>
                                    <a className="social-login__icon fab fa-facebook">
                                        <FontAwesomeIcon icon={faFacebook} />
                                    </a>
                                    <a className="social-login__icon fab fa-twitter">
                                        <FontAwesomeIcon icon={faTwitter} />
                                    </a>
                                    <a className="social-login__icon fab fa-google">
                                        <FontAwesomeIcon icon={faGoogle} />
                                    </a>
                                </div>
                            </div>
                        </Col>
                    </Row>
                </div>
            </div>
        </div>
    );
}
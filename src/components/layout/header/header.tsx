import * as React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import '../header/header.css'
import { useSelector } from 'react-redux';
import { Col, Row } from 'react-bootstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPen, faRightFromBracket, faUser } from '@fortawesome/free-solid-svg-icons';
import { AppState } from '../../../redux/app-state';
import { useState } from 'react';

const Header = (props: any) => {

    const navigate = useNavigate();
    const decodedToken = useSelector((state: AppState) => state.decodedToken);
    const [navChange, setNavChange] = useState(false);

    const changeNav = () => {
        if (window.scrollY >= 10) {
            setNavChange(true)
        } else {
            setNavChange(false)
        }
    }

    window.addEventListener('scroll', changeNav)

    return (
        <div className={navChange ? 'navbar-scroll' : 'navbar-container'}>
            <Row>
                <div className='nav'>
                    <Col sm={1}>
                        <span className='nav-logo'>
                            <Link to='/home'>
                                <img src='./logo3.png' />
                            </Link>
                        </span>
                    </Col>
                    <Col sm={9}>
                        <div className="nav-group">
                            <span>
                                <Link to='/coupons'>Coupons</Link>
                            </span>
                            <span>
                                <Link to='/companies'>Companies</Link>
                            </span>
                        </div>
                    </Col>
                    <Col sm={2}>
                        <div className="nav-group-2">
                            <span className="nav-login">
                                {(decodedToken !== undefined && decodedToken?.userId != 0) ?
                                    <div title="Hello" className='header-icon'><Link to='/profile'><FontAwesomeIcon size='xl' icon={faUser} /></Link></div>
                                    : <div title="Login" className='header-icon'><Link to='/login'><FontAwesomeIcon size='xl' icon={faUser} /></Link></div>
                                }
                            </span>
                            <span className="nav-logout">
                                {(decodedToken !== undefined && decodedToken?.userId != 0) ?
                                    <div title="logout" className='header-icon'><Link to='/logout'><FontAwesomeIcon size='xl' icon={faRightFromBracket} /></Link></div>
                                    : <div title="register" className='header-icon'><Link to='/register'><FontAwesomeIcon size='xl' icon={faPen} /></Link></div>}
                            </span>
                            {/* <input id="user_icon" type="button" className="material-symbols-outlined" value="person_3" /> */}
                        </div>
                    </Col>
                </div>
            </Row>
        </div>
    );
}

export default Header;
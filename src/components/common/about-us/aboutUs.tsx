import { Col, Row } from 'react-bootstrap';
import '../../../assets/styles.css';
import './aboutUs.css';
import { useNavigate } from 'react-router-dom';
import { faFacebook, faInstagram, faPinterest, faTwitter } from '@fortawesome/free-brands-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';


const AboutUs = () => {

    const navigate = useNavigate();

    return (
        <div className="about-us-container">
            <Row>
                <div className="about-us">
                    <Col sm={6}>
                        <h1 className="about-us-heading">
                            About Us
                        </h1>
                        <h1 className="about-us-content">
                            We Are Starshaf a Big marketplace for buying coupons in various categories... its legit! <br></br> Lorem, ipsum dolor sit amet consectetur adipisicing elit. Sapiente qui explicabo cumque vel, ab libero!
                        </h1>
                        <input type="button" value="Register" className="about-us-register-button" onClick={() => navigate('/register')} />
                    </Col>
                    <Col sm={6}>
                        <img className="about-us-image" src="tickets.jpg" alt="" />
                    </Col>
                </div>
                <div className="about-us-dark">
                    <Col sm={12}>

                    </Col>
                </div>
                <div className="about-us">
                    <Col sm={12}>

                    </Col>
                </div>
                <div className="about-us-dark-footer">
                    <Col sm={9}>
                        <h1 className="about-us-content-footer">
                            Lorem, ipsum dolor sit amet consectetur adipisicing elit. Sapiente qui explicabo cumque vel, ab libero!
                        </h1>
                    </Col>
                    <Col sm={3}>
                        <div className="container-social">
                            <div className="effect amiens">
                                <div className="buttons">
                                    <a
                                        href="https://www.facebook.com/" className="fb" title="Join us on Facebook">
                                        <FontAwesomeIcon icon={faFacebook} />
                                    </a>
                                    <a
                                        href="https://twitter.com/" className="tw" title="Join us on Twitter">
                                        <FontAwesomeIcon icon={faTwitter} />
                                    </a>
                                    <a
                                        href="https://www.pinterest.com/" className="pinterest" title="Join us on Pinterest">
                                        <FontAwesomeIcon icon={faPinterest} />
                                    </a>
                                    <a
                                        href="https://www.instagram.com/" className="insta" title="Join us on Instagram">
                                        <FontAwesomeIcon icon={faInstagram} />
                                    </a>
                                </div>
                            </div>
                        </div>
                    </Col>
                </div>
                <div className="about-us">
                    <Col sm={12}>

                    </Col>
                </div>
            </Row>
        </div>
    );
}

export default AboutUs;
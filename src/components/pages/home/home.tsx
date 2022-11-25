import { Row, Col } from 'react-bootstrap'
import Banner from './banner/banner'
import { useEffect } from 'react';
import useCoupons from '../../../hooks/useCoupons/useCoupons';
import CardsContainer from '../../resources/coupons/cardsContainer/cardsContainer';
import AboutUs from '../../common/about-us/aboutUs';
import { useNavigate } from 'react-router-dom';
import { faFacebook, faInstagram, faPinterest, faTwitter } from '@fortawesome/free-brands-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import '../../../assets/styles.css';
import './home.css';
import '../../../components/common/about-us/aboutUs.css';

const Home = () => {

    const navigate = useNavigate();

    const {
        coupons,
        pagination,
        filters
    } = useCoupons({ isAllowAll: false });

    const {
        coupons: coupons2,
        pagination: pagination2,
        filters: filters2
    } = useCoupons({ isAllowAll: false });

    useEffect(() => {
        filters.onCategoryChange('WANDS');
        filters2.onCompanyChange(1);
    }, [filters, filters2]);

    return (
        <div className="home">
            <Row>
                <Col sm={12}>
                    <Banner />
                </Col>
                <Col sm={12}>
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
                                <Col sm={6}>
                                    <img className="about-logos" src="./img/logos-work.png" alt="" />
                                </Col>
                                <Col sm={6}>
                                    <h1 className="logo-heading">
                                        Some of the brands you can find here!
                                    </h1>
                                    <h1 className="about-us-content">
                                        You can find here coupons for the coolest, rarest and most unique products and fan collectibles for the best prices!
                                    </h1>
                                </Col>
                            </div>
                            <div className="about-us">
                                <Col sm={4}>
                                    <div className='goals'>
                                        <h1 className="logo-heading">
                                            Our vision
                                        </h1>
                                        <h1 className="goals-content">
                                            To be the most known marketplace for the rarest and most unique collectibles in the word!
                                        </h1>
                                    </div>
                                </Col>
                                <Col sm={4}>
                                    <div className='goals-seperate'>
                                        <h1 className="logo-heading">
                                            Our mission
                                        </h1>
                                        <h1 className="goals-content">
                                            First of all to be here for the customer and make the purchase proccess as much as easy, user friendly, secured and for the best prices
                                        </h1>
                                    </div>
                                </Col>
                                <Col sm={4}>
                                    <div className='goals'>
                                        <h1 className="logo-heading">
                                            Our values
                                        </h1>
                                        <h1 className="goals-content">
                                            User friendly, secured, and best prices
                                        </h1>
                                    </div>
                                </Col>
                            </div>
                            <div className="about-us-dark-footer">
                                <Col sm={9}>
                                    <h1 className="about-us-content-footer">
                                        You can like and follow us here for news and updates!
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
                            <div className="home-coupons-display">
                                <Col sm={12}>
                                    <h1 className="some-coupons">Pokemon</h1>
                                    <div className="cards-container">
                                        <CardsContainer
                                            coupons={coupons2}
                                            pagination={pagination2}
                                        />
                                    </div>
                                </Col>
                            </div>
                            <div className="about-us-dark-footer">
                                <Col sm={7}>
                                    <h1 className="about-us-content-link">
                                        More from Pokemon!
                                    </h1>
                                </Col>
                                <Col sm={5}>
                                    <input type="button" value="Pokemon" className="go-to-link-button" onClick={() => navigate('/register')} />
                                </Col>
                            </div>
                            <div className="home-coupons-display">
                                <Col sm={12}>
                                    <h1 className="some-coupons"> Wands From The Wizards World!</h1>
                                    <div className="cards-container">
                                        <CardsContainer
                                            coupons={coupons}
                                            pagination={pagination}
                                        />
                                    </div>
                                </Col>
                            </div>
                            <div className="about-us-dark-footer">
                                <Col sm={8}>
                                    <h1 className="about-us-content-link">
                                        More from The Harry Potter world!
                                    </h1>
                                </Col>
                                <Col sm={4}>
                                    <input type="button" value="Pokemon" className="go-to-link-button" onClick={() => navigate('/register')} />
                                </Col>
                            </div>
                            <div className="about-us">
                                <div className='to-all'>
                                    <Col sm={6}>
                                        <h1 className="about-us-heading">
                                            To coupons page
                                        </h1>
                                        <h1 className="about-us-content">
                                            We have more than 100 coupons updated daily!
                                        </h1>
                                        <input type="button" value="Enter" className="go-to-link-button-2" onClick={() => navigate('/register')} />
                                    </Col>
                                    <Col sm={6}>
                                        <h1 className="about-us-heading">
                                            To brands page
                                        </h1>
                                        <h1 className="about-us-content">
                                            We work with 12 brands to this moment and hope to grow forther!
                                        </h1>
                                        <input type="button" value="Enter" className="go-to-link-button-2" onClick={() => navigate('/register')} />
                                    </Col>
                                </div>
                            </div>
                            <div className="about-us-dark-footer">
                                <Col sm={9}>
                                    <h1 className="about-us-contact-link">
                                        We are here for you, for any problem or question you can contact us here
                                    </h1>
                                </Col>
                                <Col sm={3}>
                                    <input type="button" value="contact" className="go-to-link-button" onClick={() => navigate('/register')} />
                                </Col>
                            </div>
                        </Row>
                    </div>
                </Col>
            </Row >
        </div>



































        // <Row>
        //     <Col sm={12}>
        //         <Banner />
        //     </Col>
        //     <Col sm={12}>
        //         <h1 className="main-heading">
        //             Wands From The Wizards World!
        //         </h1>
        //         <div className="cards-container">
        //             <CardsContainer
        //                 coupons={coupons}
        //                 pagination={pagination}
        //             />
        //         </div>
        //     </Col>
        //     <Col sm={12}>
        //         <h1 className="main-heading">
        //             Pokemon
        //         </h1>
        //         <div className="cards-container">
        //             <CardsContainer
        //                 coupons={coupons2}
        //                 pagination={pagination2}
        //             />
        //         </div>
        //     </Col>
        // </Row>
    );
}


export default Home
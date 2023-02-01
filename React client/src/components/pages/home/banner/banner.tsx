import './banner.css';
import { useNavigate } from 'react-router-dom';
import '../../../../assets/styles.css';

const Banner = () => {

    const navigate = useNavigate();

    return (
        <div className="banner">
            <img className='banner-image' src="./bannerr.jpg" />
            <div className='banner-welcome-text'>
                <h1 className="text-banner-starshaf">Welcome to Starshaf coupons</h1>
                <h1 className='text-banner-details'>
                    Starshaf coupons is the place to find the rarest collectible cards
                    <br></br>
                    and merchendise at the best prices
                </h1>
                <h1 className='text-banner-moto'>
                    We offer coupons for the best prices with no competition
                </h1>
                <div className='banner-btn-container'>
                    <input type="button" className='to-app-btn' value='To coupons' onClick={() => navigate('/coupons')} />
                    <input type="button" className='to-register-btn' value='REGISTER NOW' onClick={() => navigate('/register')} />
                </div>
            </div>
        </div>
    );
}

export default Banner;

import './unauthorized.css';
import '../../../assets/styles.css';


const Unauthorized = () => {
    return (
        <div className="page-not-found">
            <h1 className="page-not-found-main-header">
                403
            </h1>
            <h1 className="page-not-found-sub-header">
                Unauthorized
            </h1>
        </div>
    );
}

export default Unauthorized;
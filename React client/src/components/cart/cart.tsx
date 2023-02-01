import { useSelector } from "react-redux";
import './cart.css';
import '../../assets/styles.css'
import { IPurchaseRequest } from '../../models/IPurchaseRequest';
import { AppState } from "../../redux/app-state";
import CartCard from "./CartCard/cartCard";
import AppModal from "../common/AppModal/AppModal";
import { Link } from "react-router-dom";
import Checkout from "./checkout/checkout";


const Cart = () => {

    const cart: IPurchaseRequest[] = useSelector((state: AppState) => state.cart);

    function onRemoveItem(id: number) {
        let updatedCart = cart.filter(cartItem => cartItem.couponId !== id);
        localStorage.setItem('cart', JSON.stringify(updatedCart));
        window.location.reload();
    }

    return (
        <div className="cart-container">
            <h5 className="customer-page-cart-header">
                cart
            </h5>
            {cart.length === 0 && (
                <div className="empty">
                    <h5>Nothing here yet</h5>
                    <Link to="/coupons">To Coupons</Link>
                </div>
            )}
            {cart.map(purchase => (
                <CartCard
                    key={`cart-card${purchase.couponId * purchase.userId * purchase.amount * Math.random()}`}
                    couponId={purchase.couponId}
                    amount={purchase.amount}
                    onRemoveItem={onRemoveItem}
                />
            ))}
            {cart.length !== 0 &&
                <AppModal
                    title="CHECKOUT"
                    button="to-checkout"
                >
                    <Checkout />
                </AppModal>}
        </div>

    );
}

export default Cart
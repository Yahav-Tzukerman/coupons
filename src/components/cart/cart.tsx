import { Col, Row, Table, Image } from "react-bootstrap";
import { useDispatch, useSelector } from "react-redux";
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
    const dispatch = useDispatch();

    function onRemoveItem(id: number) {
        let updatedCart = cart.filter(cartItem => cartItem.couponId !== id);
        // dispatch({ type: ActionType.ON_REMOVE_CART_ITEM, payload: { ...updatedCart } });
        localStorage.setItem('cart', JSON.stringify(updatedCart));
        window.location.reload();
    }

    // dispatch({ type: ActionType.OnRemoveItem, payload: ... });

    let purchaseObj: IPurchaseRequest = {
        userId: 1,
        couponId: 1,
        amount: 1
    }

    function onPurchase() {

        // for (let i = 0; i < cart.length; i++) {
        //     PurchaseService.purchase(cart[i] ? cart[i] : purchaseObj).then((response) => {
        //         console.log(response.data);
        //         localStorage.setItem('purchase', JSON.stringify(response.data));

        //     }).catch((error) => {
        //         localStorage.setItem('purchaseError', JSON.stringify(error.response));
        //         alert(error.response.data.message)
        //     });
        // }
        // localStorage.removeItem('cart');
        // window.location.reload();
    }



    // useEffect(() => {

    // }, [])

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





function useEffect(arg0: () => void, arg1: IPurchaseRequest[][]) {
    throw new Error("Function not implemented.");
}
// function onPurchase() {
//     for (let i = 0; i < cart.length; i++) {
//         PurchaseService.purchase(cart[i] ? cart[i] : purchaseRequestObj).then((response) => {
//             console.log(response.data);
//             localStorage.setItem('purchase', JSON.stringify(response.data));

//         }).catch((error) => {
//             localStorage.setItem('purchaseError', JSON.stringify(error.response));
//             alert(error.response.data.message)
//         });
//     }
//     localStorage.removeItem('cart');
//     window.location.reload();
// }
import { Col, Row, Table, Image } from "react-bootstrap";
import { useSelector } from "react-redux";
import './cart.css';
import '../../assets/styles.css'
import { IPurchaseRequest } from '../../models/IPurchaseRequest';
import { AppState } from "../../redux/app-state";
import PurchaseService from "../../services/purchase.service";
import CartCard from "./CartCard/cartCard";


const Cart = () => {

    const cart: IPurchaseRequest[] = useSelector((state: AppState) => state.cart);

    let purchaseObj: IPurchaseRequest = {
        userId: 1,
        couponId: 1,
        amount: 1
    }

    function onPurchase() {
        for (let i = 0; i < cart.length; i++) {
            PurchaseService.purchase(cart[i] ? cart[i] : purchaseObj).then((response) => {
                console.log(response.data);
                localStorage.setItem('purchase', JSON.stringify(response.data));

            }).catch((error) => {
                localStorage.setItem('purchaseError', JSON.stringify(error.response));
                alert(error.response.data.message)
            });
        }
        localStorage.removeItem('cart');
        window.location.reload();
    }

    return (
        <div className="cart-container">
            <h5 className="customer-page-cart-header">
                cart
            </h5>
            {cart.map(purchase => (
                <CartCard
                    key={`cart-card${purchase.couponId * purchase.userId * purchase.amount * Math.random()}`}
                    couponId={purchase.couponId}
                    amount={purchase.amount}
                />
            ))}
            <input type="button" value="Purchase Now!" className="cart-purchase" onClick={onPurchase} />
        </div>

    );
}

export default Cart




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
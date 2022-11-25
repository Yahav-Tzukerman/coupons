import React, { useEffect, useState } from "react";
import CouponsService from "../../services/coupon.service";
import { IPurchaseRequest } from "../../models/IPurchaseRequest";
import { useDispatch, useSelector } from "react-redux";
import { AppState } from "../../redux/app-state";
import { ActionType } from "../../redux/action-type";
import { useNavigate } from "react-router-dom";
import { confirmAlert } from 'react-confirm-alert';
import 'react-confirm-alert/src/react-confirm-alert.css';
import "./useCouponAction.css";


function useCouponsActions() {

    const decodedToken = useSelector((state: AppState) => state.decodedToken);
    const cart = useSelector((state: AppState) => state.cart);
    const dispatch = useDispatch();
    const navigate = useNavigate();

    function getCouponById(id: number) {
        CouponsService.getCouponById(id).then(response => {
            return response.data;
        })
    };

    function onBuyCoupon(id: number, amount: number) {

        let purchaseRequestObj: IPurchaseRequest = {
            couponId: id,
            userId: decodedToken?.userId,
            amount: amount,
        }

        cart.push(purchaseRequestObj);
        dispatch({ type: ActionType.ADD_TO_CART, payload: { ...cart } });
        localStorage.setItem('cart', JSON.stringify(cart));

        confirmAlert({
            title: 'Added to cart',
            message: 'Do you want to go to cart?',
            buttons: [
                {
                    label: 'Go to cart',
                    onClick: () => navigate("/profile")
                },
                {
                    label: 'Stay here',
                    onClick: () => window.location.reload()
                }
            ]
        });
    };

    function deleteRequest(id: number) {
        CouponsService.deleteCoupon(id).then((response) => {
            alert(`coupon ${id} was deleted successfully!`);
            window.location.reload();
        }).catch((error) => {
            alert(error.response.data.message);
        });
    }

    function onDeleteCoupon(id: number) {
        confirmAlert({
            title: 'Confirm to delete',
            message: 'Are you sure you want to delete this coupon?',
            buttons: [
                {
                    label: 'Yes',
                    onClick: () => deleteRequest(id)
                },
                {
                    label: 'No',
                }
            ]
        });
    }

    return {
        onDeleteCoupon,
        onBuyCoupon,
        getCouponById
    }
}

export default useCouponsActions;
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
import { createListenerMiddleware } from "@reduxjs/toolkit";
import { convertTypeAcquisitionFromJson } from "typescript";


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

    async function onBuyCoupon(id: number, amount: number) {

        let purchaseRequestObj: IPurchaseRequest = {
            couponId: id,
            userId: decodedToken?.userId,
            amount: amount,
        }

        let checkIfExist = cart.filter(cartItem => cartItem.couponId === id);

        let coupon = await CouponsService.getCouponById(id).then(response => response.data);

        if (coupon.amount >= amount) {
            if (checkIfExist.length === 0) {
                cart.push(purchaseRequestObj);
            }
            else {
                cart.map(cartItem => {
                    if (cartItem.couponId === id) {
                        cartItem.amount = amount;
                    }
                })
            }
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
                    }
                ]
            });
        }
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
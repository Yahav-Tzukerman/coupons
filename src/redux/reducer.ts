import { AppState } from "./app-state";
import { Action } from "./actions";
import { ActionType } from "./action-type";
import { ICoupon } from '../models/ICoupon';
import { IUserResponse } from '../models/IUserResponse';

const appStateInitialValue = new AppState();

// This function is NOT called direcrtly by you
export function reduce(oldAppState: AppState = appStateInitialValue, action: Action): AppState {

    let currentUser: IUserResponse = {
        id: 0,
        username: "",
        firstName: "",
        lastName: "",
        phone: "",
        joinDate: new Date(),
        role: "",
        companyId: 0,
        token: ""
    };

    let decodedToken = null;
    let cart = null;
    let state = null;

    switch (action.type) {
        case ActionType.OnLogin:
            currentUser = action.payload.currentUser;
            decodedToken = action.payload.decodedToken;
            state = { ...oldAppState, currentUser };
            return { ...state, decodedToken }
        case ActionType.OnLogout:
            currentUser = action.payload.currentUser;
            decodedToken = action.payload.decodedToken;
            state = { ...oldAppState, currentUser };
            return { ...state, decodedToken }
        case ActionType.AppCoupons:
            let coupons: ICoupon[] = action.payload;
            return { ...oldAppState, ...coupons }
        case ActionType.ADD_TO_CART:
            cart = action.payload.cart;
            return { ...oldAppState, ...cart }
        case ActionType.ON_REMOVE_CART_ITEM:
            cart = action.payload;
            return { ...oldAppState, ...cart }
        default:
            return oldAppState
    }
}
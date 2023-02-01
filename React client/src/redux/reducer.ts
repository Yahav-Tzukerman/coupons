import { AppState } from "./app-state";
import { Action } from "./actions";
import { ActionType } from "./action-type";
import { ICoupon } from '../models/ICoupon';
import { IUserResponse } from '../models/IUserResponse';

const appStateInitialValue = new AppState();

// This function is NOT called direcrtly by you
export function reduce(oldAppState: AppState = appStateInitialValue, action: Action): AppState {

    let decodedToken = null;
    let cart = null;
    let state = null;

    switch (action.type) {
        case ActionType.OnLogin:
            decodedToken = action.payload.decodedToken;
            return { ...oldAppState, decodedToken };
        case ActionType.OnLogout:
            decodedToken = action.payload.decodedToken;
            return { ...oldAppState, decodedToken };
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
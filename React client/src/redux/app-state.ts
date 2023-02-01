import { IUserResponse } from '../models/IUserResponse';
import { ICoupon } from '../models/ICoupon';
import jwt from 'jwt-decode';
import { IDecodedToken } from '../models/IDecodedToken';
import { IPurchaseRequest } from '../models/IPurchaseRequest';
import { IPurchaseResponse } from '../models/IPurchaseResponse';
export class AppState {

      public cart: IPurchaseRequest[] = localStorage.getItem('cart') == undefined ? [] : JSON.parse(localStorage.getItem('cart') || '');

      purchased: IPurchaseResponse[] = [];

      public decodedToken: IDecodedToken = localStorage.getItem('token') == undefined ? {sub: '', roles: '' ,userId:0 ,exp:0} : jwt(localStorage.getItem('token') || '');
      
}
import axios from "axios"
import { BASE_URL } from "../constants/matcher";
import { ICoupon } from '../models/ICoupon';

const addCoupon = (coupon: ICoupon) => {
  const json = JSON.stringify(coupon);
  return axios.post(BASE_URL + `/coupons`, json, {
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${localStorage.getItem('token')}`
    }
  });
};

const updateCoupon = (coupon: ICoupon) => {
  const json = JSON.stringify(coupon);
  return axios.put(BASE_URL + `/coupons`, json, {
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${localStorage.getItem('token')}`
    }
  });
};

const deleteCoupon = (id: number) => {
  return axios.delete(BASE_URL + `/coupons/${id}`, {
    headers: {
      'Authorization': `Bearer ${localStorage.getItem('token')}`
    }
  });
};

const getCouponById = (id: number) => {
  return axios.get(BASE_URL + `/coupons/${id}`)
};

const getAllCoupons = (filter: string, pageNumber: number, pageSize: number, companyId: number, maxPrice: number, category: string, searchedTitle: string, sortBy?: string, isDescending?: boolean) => {
  return axios.get(BASE_URL + `/coupons?filter=${filter}&pageNumber=${pageNumber}&pageSize=${pageSize}&companyId=${companyId}&maxPrice=${maxPrice}&category=${category}&searchedTitle=${searchedTitle}&sortBy=${sortBy}&isDescending=${isDescending}`)
};

const CouponsService = { addCoupon, getAllCoupons, deleteCoupon, updateCoupon, getCouponById };

export default CouponsService;

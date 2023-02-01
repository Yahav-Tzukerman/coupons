import axios from "axios"
import { BASE_URL } from "../constants/matcher";
import { IPurchaseRequest } from "../models/IPurchaseRequest";


const purchase = async(purchase: IPurchaseRequest) => {
    const json = JSON.stringify(purchase);
    return axios.post( BASE_URL + `/purchases`, json, {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      }
    });
  }

  const getPurchasesByUserId = (pageNumber: number, pageSize: number, userId: number) => {
    return axios.get( BASE_URL + `/purchases/byUser/${userId}?pageNumber=${pageNumber}&pageSize=${pageSize}` ,{
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      }
    });
};

const getPurchasesAmount = () => {
  return axios.get( BASE_URL + `/purchases/amount` ,{
    headers: {
      'Authorization': `Bearer ${localStorage.getItem('token')}`
    }
  });
};

const getYearlyRevenue = () => {
  return axios.get(
    BASE_URL + `/purchases/revenue`,
    {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      },
    }
  );
};

const PurchaseService = { purchase, getPurchasesByUserId, getPurchasesAmount, getYearlyRevenue };

export default PurchaseService;
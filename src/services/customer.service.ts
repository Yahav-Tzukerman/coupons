import axios from "axios"
import { BASE_URL } from "../constants/matcher";
import { ICustomerRequest } from '../models/ICustomerRequest';

const register = async( customer: ICustomerRequest) => {
    const json = JSON.stringify(customer);
    return axios.post(BASE_URL + `/customers`, json, {
      headers: {
        'Content-Type': 'application/json'
      }
    });
  }

  const getCustomerById = (id: number) => {
    return axios.get( BASE_URL + `/customers/${id}`, {
      headers: {
          'Authorization': `Bearer ${localStorage.getItem('token')}`
      },
    });
  };
const CustomerService = { register, getCustomerById };

export default CustomerService;
import axios from "axios"
import { BASE_URL } from "../constants/matcher";
import { IRequestUser } from '../models/IRequestUser';

const register = async( user: IRequestUser) => {
  const json = JSON.stringify(user);
  return axios.post(BASE_URL + `/users`, json, {
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${localStorage.getItem('token')}`
    }
  });
}

const login = async (username: string, password: string) => {
    const json = JSON.stringify({username: username, password: password});
    return axios.post(BASE_URL + `/users/login`, json, {
        headers: {
          'Content-Type': 'application/json'
        }
    });
};

const getAllUsers = (pageNumber: number, pageSize: number) => {
    return axios.get( BASE_URL + `/users?pageNumber=${pageNumber}&pageSize=${pageSize}` ,{
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      }
    });
};

const addUser = (user: IRequestUser) => {
  const json = JSON.stringify(user);
  return axios.post(BASE_URL + `/users`, json, {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      }
    });
};

const updateUser = (user: IRequestUser) => {
const json = JSON.stringify(user);
return axios.put(BASE_URL + `/users`, json, {
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${localStorage.getItem('token')}`
    }
  });
};

const deleteUser = (id: number) => {
  return axios.delete(BASE_URL + `/users/${id}`,{
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      }
  });
};

const getUserById = (id: number) => {
  return axios.get( BASE_URL + `/users/${id}`,{
    headers: {
      'Authorization': `Bearer ${localStorage.getItem('token')}`
    }
});
};

const UserService = { login, register, getAllUsers, addUser, updateUser, deleteUser, getUserById };

export default UserService;
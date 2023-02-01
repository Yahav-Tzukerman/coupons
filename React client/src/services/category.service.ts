import axios from "axios"
import { BASE_URL } from '../constants/matcher';
import { ICategory } from '../models/ICategory';

const getAllCategories = () => {
    return axios.get(BASE_URL + `/categories`);
};

const addCategory = (category: ICategory) => {
    const json = JSON.stringify(category);
    return axios.post(BASE_URL + `/categories`, json, {
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
      });
};

const updateCategory = (category: ICategory) => {
    const json = JSON.stringify(category);
    return axios.put(BASE_URL + `/categories`, json, {
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
      });
    };

const deleteCategory = (id: number) => {
  return axios.delete(BASE_URL + `/categories/${id}`,{
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      }
  });
};

const CategoriesService = { getAllCategories, addCategory, updateCategory, deleteCategory};

export default CategoriesService;
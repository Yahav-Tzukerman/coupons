import axios from "axios"
import { BASE_URL } from '../constants/matcher';
import { ICompany } from '../models/ICompany';

const getAllCompanies = async(pageNumber: number, pageSize: number) => {
    return axios.get(BASE_URL + `/companies?pageNumber=${pageNumber}&pageSize=${pageSize}`);
  }

const getCompany = async(companyId: string | undefined) => {
  return axios.get(BASE_URL + `/companies/${companyId}`);
}

const getCompaniesList = async() => {
  return axios.get(BASE_URL + `/companies/list`);
}

const addCompany = (company: ICompany) => {
  const json = JSON.stringify(company);
  return axios.post(BASE_URL + `/companies`, json, {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      }
    });
};

const updateCompany = (company: ICompany) => {
const json = JSON.stringify(company);
return axios.put(BASE_URL + `/companies`, json, {
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${localStorage.getItem('token')}`
    }
  });
};

const deleteCompany = (id: number) => {
  return axios.delete(BASE_URL + `/companies/${id}`,{
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      }
  });
};

const CompanyService = { getAllCompanies, getCompany, getCompaniesList, addCompany, updateCompany, deleteCompany };

export default CompanyService;
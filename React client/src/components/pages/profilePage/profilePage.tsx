import React from "react";
import { useSelector } from "react-redux";
import { AppState } from "../../../redux/app-state";
import AdminPage from "../admin/adminPage";
import './profilePage.css';
import CompanyUserPage from '../usersPages/companyUserPage/companyUserPage';
import CustomerPage from '../usersPages/customerPage/customerPage';



interface IProps {

}

const ProfilePage: React.FC<IProps> = () => {

    const decodedToken = useSelector((state: AppState) => state.decodedToken);

    if (decodedToken?.roles === "ROLE_ADMIN") {
        return <AdminPage />
    }
    if (decodedToken?.roles === "ROLE_COMPANY") {
        return <CompanyUserPage />
    }
    return <CustomerPage />
}

export default ProfilePage;
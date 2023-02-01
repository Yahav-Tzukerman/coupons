import { useSelector } from "react-redux";
import { Route, Routes } from "react-router-dom";
import "./App.css";
import './App.css'
import Cart from "./components/cart/cart";
import Unauthorized from "./components/pages/403/unauthorized";
import PageNotFound from "./components/pages/404/pageNotFound";
import AdminPage from "./components/pages/admin/adminPage";
import CompaniesPage from "./components/pages/companiesPage/companiesPage";
import CompanyPage from "./components/pages/companyPage/companyPage";
import CouponsPage from "./components/pages/couponsPage/couponsPage";
import Home from "./components/pages/home/home";
import Login from "./components/pages/login/login";
import Logout from "./components/pages/logout/logout";
import ProfilePage from "./components/pages/profilePage/profilePage";
import Register from "./components/pages/register/register";
import { AppState } from "./redux/app-state";


function App() {

  return (
    <Routes>
      <Route path="/register" element={<Register />} />
      <Route path="/home" element={<Home />} />
      <Route path="/login" element={<Login />} />
      <Route path="/logout" element={<Logout />} />
      <Route path="/cart" element={<Cart />} />
      <Route path="/" element={<CouponsPage />} />
      <Route path="/coupons" element={<CouponsPage />} />
      <Route path="/companies" element={<CompaniesPage />} />
      <Route path="/company/:companyId" element={<CompanyPage />} />
      <Route path="/profile" element={<ProfilePage />} />
      <Route path='/404' element={<PageNotFound />} />
      <Route path='/403' element={<Unauthorized />} />
    </Routes>
  );
}

export default App;

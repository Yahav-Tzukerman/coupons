import React, { useEffect, useState } from "react"
import { NAME_REGEX, PHONE_REGEX } from "../../../constants/matcher";
import { ICompany } from "../../../models/ICompany";
import CompanyService from "../../../services/company.service";
import './Form.css';


export interface IAddOrEditCompanyProps {
    company?: ICompany;
}

const AddOrEditCompany = ({ company }: IAddOrEditCompanyProps) => {

    const defaultCompany: ICompany = {
        id: 0,
        name: "",
        address: "",
        phone: "",
        foundationDate: "",
        logo: ""
    }

    // const coupons = useSelector((state: AppState) => state.coupons);
    const [name, setName] = useState<string>("");
    const [address, setAddress] = useState<string>("");
    const [phone, setPhone] = useState<string>("");
    const [foundationDate, setFoundationDate] = useState<string>("");
    const [logo, setLogo] = useState<string>("");
    const [error, setError] = useState<string>("");


    useEffect(() => {
        load();
    }, []);

    const load = () => {
        if (company?.id) {
            setName(company.name);
            setAddress(company.address);
            setPhone(company.phone);
            setFoundationDate(company.foundationDate);
            setPhone(company.phone);
            setLogo(company.logo);
        }
    }

    const initDefaultCoupon = () => {
        defaultCompany.id = company?.id === undefined ? 0 : company.id;
        defaultCompany.name = name;
        defaultCompany.address = address;
        defaultCompany.foundationDate = foundationDate;
        defaultCompany.phone = phone;
        defaultCompany.logo = logo;
    }


    const onEditCompany = () => {
        initDefaultCoupon();
        console.log(JSON.stringify(defaultCompany));


        CompanyService.updateCompany(defaultCompany).then(response => {
            localStorage.setItem('updatedCompany', JSON.stringify(response.data));
            window.location.reload();
        }).catch(error => {
            setError(error.response.data.message);
            localStorage.setItem('addError', error);
        });
    }

    const onAddCompany = () => {
        initDefaultCoupon();

        localStorage.setItem('addeddCompany', JSON.stringify(defaultCompany));

        CompanyService.addCompany(defaultCompany).then(response => {
            localStorage.setItem('addedCouponn', JSON.stringify(response.data));
            window.location.reload();
        }).catch(error => {
            setError(error.response.data.message);
            localStorage.setItem('addError', error);
        });
    }

    return (
        <div className="add-coupon-container">
            <h1 className="add-coupon-header">{company?.id ? "EDIT" : "ADD"} Companies</h1>
            <form>
                <div className="add-coupon-form">
                    <div className="modal-form__group">
                        <input
                            type="text"
                            placeholder={company?.name ? company.name : "Enter Name"}
                            name="name"
                            id="name"
                            className="add-coupon-input"
                            onChange={(e) => setName(e.target.value)}
                            required
                            onFocus={(e) => {
                                e.target.value = `${name}`;
                                e.target.id = "";
                            }}
                            onBlur={(e) => {
                                if (!NAME_REGEX.exec(e.target.value)) {
                                    e.target.type = "text"
                                    e.target.value = "invalid name"
                                    e.target.id = "input-error";
                                }
                            }}
                        />
                    </div>
                    <div className="modal-form__group">
                        <input
                            type="text"
                            placeholder={company?.address ? company.address : "Enter Address"}
                            name="address"
                            id="address"
                            className="add-coupon-input"
                            onChange={(e) => setAddress(e.target.value)}
                            required
                            onFocus={(e) => {
                                e.target.value = `${address}`;
                                e.target.id = "";
                            }}
                            onBlur={(e) => {
                                if (!NAME_REGEX.exec(e.target.value)) {
                                    e.target.value = "invalid address"
                                    e.target.id = "input-error";
                                }
                            }}
                        />
                    </div>
                    <div className="modal-form__group">
                        <input
                            type="text"
                            placeholder={company?.phone ? company.phone : "Enter Phone"}
                            name="phone"
                            id="phone"
                            className="add-coupon-input"
                            onChange={(e) => setPhone(e.target.value)}
                            required
                            onFocus={(e) => {
                                e.target.value = `${phone}`;
                                e.target.id = "";
                            }}
                            onBlur={(e) => {
                                if (!PHONE_REGEX.exec(e.target.value)) {
                                    e.target.type = "text"
                                    e.target.value = "invalid phone number"
                                    e.target.id = "input-error";
                                }
                            }}
                        />
                    </div>
                    <div className="modal-form__group">
                        <input
                            type="text"
                            placeholder={company?.foundationDate ? company.foundationDate : "Enter foundation date"}
                            name="foundationDate"
                            id="foundationDate"
                            className="add-coupon-input"
                            onChange={(e) => setFoundationDate(e.target.value)}
                            required
                            onFocus={(e) => (e.target.type = "date")}
                            onBlur={(e) => (e.target.type = "text")}
                        />
                    </div>
                    <div className="modal-form__group">
                        <input
                            type="text"
                            placeholder={company?.logo ? company.logo : "Enter logo"}
                            name="logo"
                            id="logo"
                            className="add-coupon-input"
                            onChange={(e) => setLogo(e.target.value)}
                            required
                            onFocus={(e) => {
                                e.target.value = `${logo}`;
                                e.target.id = "";
                            }}
                            onBlur={(e) => {
                                if (!logo.endsWith('.png') && !logo.endsWith('.jpg')) {
                                    e.target.value = "Image url must end with .png"
                                    e.target.id = "input-error";
                                }
                            }}
                        />
                    </div>

                    {error !== "" && <div className="form-error">{error}</div>}
                    <div className="">
                        {!company?.id && <input type="button" className="add-coupon-button" value="add company" onClick={onAddCompany} />}
                        {company?.id && <input type="button" className="add-coupon-button" value="edit company" onClick={onEditCompany} />}
                    </div>
                </div>
            </form>
        </div>
    );
};

export default AddOrEditCompany;
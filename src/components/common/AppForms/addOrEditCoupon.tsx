import React, { useEffect, useState } from "react"
import { CODE_REGEX, NAME_REGEX } from "../../../constants/matcher";
import { ICompanyList } from "../../../models/ICompanyList";
import { ICoupon } from "../../../models/ICoupon";
import CouponsService from "../../../services/coupon.service";
import CategoriesDropDown from "../../resources/categories/categoriesDropDown/categoriesDropDown";
import CompaniesDropDown from "../../resources/companies/companiesDropDown/companiesDropDown";
import './Form.css';

export interface IAddCouponProps {
    coupon?: ICoupon;
}

const AddOrEditCoupon = ({ coupon }: IAddCouponProps) => {

    const defaultCoupon: ICoupon = {
        id: 0,
        code: "",
        title: "",
        description: "",
        startDate: "",
        endDate: "",
        category: "",
        amount: 0,
        price: 0,
        companyId: 0,
        companyName: "",
        imageUrl: ""
    }

    // const coupons = useSelector((state: AppState) => state.coupons);
    const [code, setCode] = useState<string>("");
    const [title, setTitle] = useState<string>("");
    const [description, setDescription] = useState<string>("");
    const [startDate, setStartDate] = useState<string>("");
    const [endDate, setEndDate] = useState<string>("");
    const [category, setCategory] = useState<string>("");
    const [amount, setAmount] = useState<number>(0);
    const [price, setPrice] = useState<number>(0);
    const [companyId, setCompanyId] = useState<number>(0);
    const [imageUrl, setImageUrl] = useState<string>("");
    const [error, setError] = useState<string>("");
    const [companyList, setCompanyList] = useState<ICompanyList>();

    useEffect(() => {
        load();
    }, []);

    const load = () => {
        if (coupon?.id) {
            setCode(coupon.code);
            setTitle(coupon.title);
            setDescription(coupon.description);
            setStartDate(coupon.startDate);
            setEndDate(coupon.endDate);
            setCategory(coupon.category);
            setAmount(coupon.amount);
            setPrice(coupon.price);
            setCompanyId(coupon.companyId);
            setImageUrl(coupon.imageUrl);
        }
    }

    const initDefaultCoupon = () => {
        defaultCoupon.id = coupon?.id === undefined ? 0 : coupon.id;
        defaultCoupon.code = code;
        defaultCoupon.title = title;
        defaultCoupon.description = description;
        defaultCoupon.startDate = startDate;
        defaultCoupon.endDate = endDate;
        defaultCoupon.category = category;
        defaultCoupon.amount = amount;
        defaultCoupon.price = price;
        defaultCoupon.companyId = companyId;
        defaultCoupon.imageUrl = imageUrl;
    }


    const onEditCoupon = () => {
        initDefaultCoupon();
        console.log(JSON.stringify(defaultCoupon));


        CouponsService.updateCoupon(defaultCoupon).then(response => {
            localStorage.setItem('updatedCouponn', JSON.stringify(response.data));
            window.location.reload();
        }).catch(error => {
            setError(error.response.data.message);
            localStorage.setItem('addError', error);
        });
    }

    const onAddCoupon = () => {
        initDefaultCoupon();

        localStorage.setItem('addeddCoupon', JSON.stringify(defaultCoupon));

        CouponsService.addCoupon(defaultCoupon).then(response => {
            localStorage.setItem('addedCouponn', JSON.stringify(response.data));
            window.location.reload();
        }).catch(error => {
            setError(error.response.data.message);
            localStorage.setItem('addError', error);
        });
    }
    const onCompanyChange = (companyId: number) => {
        setCompanyId(companyId);
    }

    return (
        <div className="add-coupon-container">
            <h1 className="add-coupon-header">{coupon?.id ? "EDIT" : "ADD"} Coupon</h1>
            <form>
                <div className="add-coupon-form">
                    <div className="modal-form__group">
                        <input
                            type="text"
                            placeholder={coupon?.code ? coupon.code : "Enter Code"}
                            name="code"
                            id="code"
                            className="add-coupon-input"
                            onChange={(e) => setCode(e.target.value)}
                            disabled={coupon ? true : false}
                            required
                            onFocus={(e) => {
                                e.target.value = `${code}`;
                                e.target.id = "";
                            }}
                            onBlur={(e) => {
                                if (!CODE_REGEX.exec(e.target.value)) {
                                    e.target.value = "uppercase letters & numbers, length 2-16"
                                    e.target.id = "input-error";
                                }
                            }}
                        />
                    </div>
                    <div className="modal-form__group">
                        <input
                            type="text"
                            placeholder={coupon?.title ? coupon.title : "Enter Title"}
                            name="title"
                            id="title"
                            className="add-coupon-input"
                            onChange={(e) => setTitle(e.target.value)}
                            required
                            onFocus={(e) => {
                                e.target.value = `${title}`;
                                e.target.id = "";
                            }}
                            onBlur={(e) => {
                                if (e.target.value.length < 2 || e.target.value.length > 30) {
                                    e.target.value = "Title: length 2-30"
                                    e.target.id = "input-error";
                                }
                            }}
                        />
                    </div>
                    <div className="modal-form__group">
                        <input
                            type="text"
                            placeholder={coupon?.description ? coupon.description : "Enter Description"}
                            name="description"
                            id="title"
                            className="add-coupon-input"
                            onChange={(e) => setDescription(e.target.value)}
                            required
                            onFocus={(e) => {
                                e.target.value = `${description}`;
                                e.target.id = "";
                            }}
                            onBlur={(e) => {
                                if (e.target.value.length < 2 || e.target.value.length > 255) {
                                    e.target.value = "Description: length 2-255"
                                    e.target.id = "input-error";
                                }
                            }}
                        />
                    </div>
                    <div className="modal-form__group">
                        <input
                            type="text"
                            onFocus={(e) => (e.target.type = "date")}
                            onBlur={(e) => (e.target.type = "text")}
                            placeholder={coupon?.startDate ? 'Start Date: '.concat(new Date(coupon.startDate).toUTCString().substring(5, 16)) : "Enter start date"}
                            name="startDate"
                            id="startDate"
                            className="add-coupon-input"
                            onChange={(e) => setStartDate(e.target.value)}
                            required
                        />
                    </div>
                    <div className="modal-form__group">
                        <input
                            type="text"
                            onFocus={(e) => (e.target.type = "date")}
                            onBlur={(e) => (e.target.type = "text")}
                            id="endDate"
                            placeholder={coupon?.endDate ? 'End Date: '.concat(new Date(coupon.endDate).toUTCString().substring(5, 16)) : "Enter end date"}
                            name="endDate"
                            className="add-coupon-input"
                            onChange={(e) => setEndDate(e.target.value)}
                            required
                        />
                    </div>
                    <div className="modal-form__group">
                        <input
                            type="number"
                            placeholder={coupon?.amount ? coupon.amount.toString() : "Enter amount"}
                            name="amount"
                            id="amount"
                            className="add-coupon-input"
                            onChange={(e) => setAmount(parseInt(e.target.value))}
                            required
                            onFocus={(e) => {
                                e.target.type = "number";
                                e.target.value = `${amount}`;
                                e.target.id = "";
                            }}
                            onBlur={(e) => {
                                if (amount < 1) {
                                    e.target.type = "text";
                                    e.target.value = "Amount: must be 1 or greater"
                                    e.target.id = "input-error";
                                }
                            }}
                        />
                    </div>
                    <div className="modal-form__group">
                        <input
                            type="number"
                            placeholder={coupon?.price ? coupon.price.toString() : "Enter price"}
                            name="price"
                            id="price"
                            className="add-coupon-input"
                            onChange={(e) => setPrice(parseInt(e.target.value))}
                            required
                            onFocus={(e) => {
                                e.target.type = "number";
                                e.target.value = `${price}`;
                                e.target.id = "";
                            }}
                            onBlur={(e) => {
                                if (price <= 0) {
                                    e.target.type = "text";
                                    e.target.value = "Price: must be greater then 0"
                                    e.target.id = "input-error";
                                }
                            }}
                        />
                    </div>
                    <div className="modal-form__group">
                        <input
                            type="text"
                            placeholder={coupon?.imageUrl ? coupon.imageUrl : "Enter image url"}
                            name="imageUrl"
                            id="imageUrl"
                            className="add-coupon-input"
                            onChange={(e) => setImageUrl(e.target.value)}
                            required
                            onFocus={(e) => {
                                e.target.value = `${imageUrl}`;
                                e.target.id = "";
                            }}
                            onBlur={(e) => {
                                if (!imageUrl.endsWith('.png') && !imageUrl.endsWith('.jpg')) {
                                    e.target.value = "Image url must end with .png"
                                    e.target.id = "input-error";
                                }
                            }}
                        />
                    </div>
                    <div className="modal-form__group dropdown-form-group">
                        <label>Company</label>
                        <CompaniesDropDown
                            selectedCompany={setCompanyId}
                            defaultCompanyId={coupon?.companyId === undefined ? 0 : coupon.companyId} />
                    </div>
                    <div className="modal-form__group dropdown-form-group">
                        <label>Category</label>
                        <CategoriesDropDown
                            selectedCategory={setCategory}
                            defaultCategory={coupon?.category}
                        />
                    </div>
                    {error !== "" && <div className="form-error">{error}</div>}
                    <div className="">
                        {!coupon?.id && <input id="form-button" type="button" className="add-coupon-button" value="add coupon" onClick={onAddCoupon} />}
                        {coupon?.id && <input id="form-button" type="button" className="add-coupon-button" value="edit coupon" onClick={onEditCoupon} />}
                    </div>
                </div>
            </form>
        </div>
    );
}

export default AddOrEditCoupon;
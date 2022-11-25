import React, { useEffect, useState } from "react"
import { ICompanyList } from "../../../models/ICompanyList";
import { ICoupon } from "../../../models/ICoupon";
import CouponsService from "../../../services/coupon.service";
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
                            required
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
                        />
                    </div>
                    <div className="modal-form__group">
                        <input
                            type="text"
                            placeholder={coupon?.description ? coupon.description : "Enter Description"}
                            name="description"
                            id="description"
                            className="add-coupon-input"
                            onChange={(e) => setDescription(e.target.value)}
                            required
                        />
                    </div>
                    <div className="modal-form__group">
                        <input
                            type="date"
                            placeholder={coupon?.startDate ? coupon.startDate : "Enter start date"}
                            name="startDate"
                            id="startDate"
                            className="add-coupon-input"
                            onChange={(e) => setStartDate(e.target.value)}
                            required
                        />
                    </div>
                    <div className="modal-form__group">
                        <input
                            type="date"
                            placeholder={coupon?.endDate ? coupon.endDate : "Enter end date"}
                            name="endDate"
                            id="endDate"
                            className="add-coupon-input"
                            onChange={(e) => setEndDate(e.target.value)}
                            required
                        />
                    </div>
                    <div className="modal-form__group">
                        <input
                            type="text"
                            placeholder={coupon?.category ? coupon.category : "Enter category"}
                            name="category"
                            id="category"
                            className="add-coupon-input"
                            onChange={(e) => setCategory(e.target.value)}
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
                        />
                    </div>
                    <CompaniesDropDown
                        selectedCompany={setCompanyId}
                        defaultCompanyId={coupon?.companyId === undefined ? 0 : coupon.companyId} />
                    <div className="modal-form__group">
                        <input
                            type="text"
                            placeholder={coupon?.imageUrl ? coupon.imageUrl : "Enter image url"}
                            name="imageUrl"
                            id="imageUrl"
                            className="add-coupon-input"
                            onChange={(e) => setImageUrl(e.target.value)}
                            required
                        />
                    </div>
                    {error !== "" && <div className="error">{error}</div>}
                    <div className="">
                        {!coupon?.id && <input type="button" className="add-coupon-button" value="add coupon" onClick={onAddCoupon} />}
                        {coupon?.id && <input type="button" className="add-coupon-button" value="edit coupon" onClick={onEditCoupon} />}
                    </div>
                </div>
            </form>
        </div>
    );
}

export default AddOrEditCoupon;
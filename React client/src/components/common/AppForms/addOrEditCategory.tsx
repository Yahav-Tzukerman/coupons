import React, { useEffect, useState } from "react"
import { NAME_REGEX } from "../../../constants/matcher";
import { ICategory } from "../../../models/ICategory";
import CategoriesService from "../../../services/category.service";
import './Form.css';


export interface IAddOrEditCategoryProps {
    category?: ICategory;
}

const AddOrEditCategory = ({ category }: IAddOrEditCategoryProps) => {

    const defaultCategory: ICategory = {
        id: 0,
        name: "",
    }

    // const coupons = useSelector((state: AppState) => state.coupons);
    const [name, setName] = useState<string>("");
    const [error, setError] = useState<string>("");


    useEffect(() => {
        load();
    }, []);

    const load = () => {
        if (category?.id) {
            setName(category.name);
        }
    }

    const initDefaultCategory = () => {
        defaultCategory.id = category?.id === undefined ? 0 : category.id;
        defaultCategory.name = name;
    }


    const onEditCategory = () => {
        initDefaultCategory();
        console.log(JSON.stringify(defaultCategory));


        CategoriesService.updateCategory(defaultCategory).then(response => {
            localStorage.setItem('updatedCategory', JSON.stringify(response.data));
            window.location.reload();
        }).catch(error => {
            setError(error.response.data.message);
            localStorage.setItem('addError', error);
        });
    }

    const onAddCategory = () => {
        initDefaultCategory();

        localStorage.setItem('addeddCoupon', JSON.stringify(defaultCategory));

        CategoriesService.addCategory(defaultCategory).then(response => {
            localStorage.setItem('addedCouponn', JSON.stringify(response.data));
            window.location.reload();
        }).catch(error => {
            setError(error.response.data.message);
            localStorage.setItem('addError', error);
        });
    }

    return (
        <div className="add-coupon-container">
            <h1 className="add-coupon-header">{category?.id ? "EDIT" : "ADD"} Category</h1>
            <form>
                <div className="add-coupon-form">
                    <div className="modal-form__group">
                        <input
                            type="text"
                            placeholder={category?.name ? category.name : "Enter Category Name"}
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
                                    e.target.value = "category is invalid"
                                    e.target.id = "input-error";
                                }
                            }}
                        />
                    </div>

                    {error !== "" && <div className="form-error">{error}</div>}
                    <div className="">
                        {!category?.id && <input type="button" className="add-coupon-button" value="add category" onClick={onAddCategory} />}
                        {category?.id && <input type="button" className="add-coupon-button" value="edit category" onClick={onEditCategory} />}
                    </div>
                </div>
            </form>
        </div>
    );
};

export default AddOrEditCategory;
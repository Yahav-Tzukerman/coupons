import React, { useEffect, useState } from "react"
import { useSelector } from "react-redux";
import { ICategory } from "../../../../models/ICategory";
import { AppState } from "../../../../redux/app-state";
import CategoriesService from "../../../../services/category.service";
import '../../companies/companiesDropDown/companiesDropDown.css';

export interface ICategoriesDropDownProps {
    selectedCategory: (category: string) => void;
    defaultCategory?: string;
}

const CategoriesDropDown = ({ selectedCategory, defaultCategory }: ICategoriesDropDownProps) => {

    const defaultCategories: ICategory[] = defaultCategory ? [{ id: 0, name: defaultCategory }] : [{ id: 0, name: "" }];
    const [categories, setCategories] = useState<ICategory[]>(defaultCategories);

    useEffect(() => {
        CategoriesService.getAllCategories().then((response) => {
            let list: ICategory[] = response.data;
            let defaultCategoryObj = categories.filter(category => category.name === defaultCategory).pop();
            let responseList: ICategory[] = [{ id: 0, name: '' }, ...list];
            setCategories(responseList);
        });
    }, []);

    return (
        <div className="dropdown-container">
            <select name="dropdown" 
                onChange={(e) => selectedCategory(e.target.value)}
                defaultValue={defaultCategory ? categories.filter(category => category.name === defaultCategory).pop()?.name : ''}>

                {categories.map(category => (
                    <option value={category.name} key={category.name} >
                        {category.name === '' ? 'Category' : category.name}
                    </option>
                ))}

            </select>
        </div>
    );
}

export default CategoriesDropDown
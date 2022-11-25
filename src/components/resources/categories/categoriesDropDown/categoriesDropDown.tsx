import React, { useEffect, useState } from "react"
import { ICategory } from "../../../../models/ICategory";
import CategoriesService from "../../../../services/category.service";


export interface ICategoriesDropDownProps {
    selectedCategory: (category: string) => void;
}

const CategoriesDropDown = ({ selectedCategory }: ICategoriesDropDownProps) => {

    const defaultCategories: ICategory[] = [{ id: 0, name: "" }];
    const [categories, setCategories] = useState<ICategory[]>(defaultCategories);
    
    useEffect(() => {
        CategoriesService.getAllCategories().then((response) => {
            let list: ICategory[] = response.data;
            let responseList: ICategory[] = [{ id: 0, name: '' }, ...list];
            setCategories(responseList);
        });
    }, []);

    return (
        <div className="dropdown-container">
            <select name="dropdown"
                onChange={(e) => selectedCategory(e.target.value)}
                defaultValue={defaultCategories.pop()?.name}>

                {categories.map(category => (
                    <option value={category.name} key={category.id} >
                        {category.name}
                    </option>
                ))}

            </select>
        </div>
    );
}

export default CategoriesDropDown
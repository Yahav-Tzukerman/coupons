import React, { useEffect, useState } from "react";
import { ICategory } from "../../models/ICategory";
import CategoriesService from "../../services/category.service";
import usePagination from "../usePagination/usePagination";


function useCategories() {

    const [categories, setCategories] = useState<ICategory[]>([]);
    const { totalElements, pageSize, pageNumber, setPageNumber, setTotalElements, setPageSize } = usePagination();

    function onDeleteCategory(id: number) {

        CategoriesService.deleteCategory(id).then((response) => {
            alert(`coupon ${id} was deleted successfully!`)
        }).catch((error) => {
            alert(error.response.data.message);
        });
        window.location.reload();
    }

    useEffect(() => {
        CategoriesService.getAllCategories().then((response) => {
            setCategories(response.data);
            setTotalElements(categories.length);
        });
    }, [pageNumber]);

    return {
        categories,
        categoriesPagination: { totalElements, pageSize, pageNumber, setPageNumber, setTotalElements, setPageSize },
        onDeleteCategory
    }
}

export default useCategories;
import React, { useEffect, useState } from "react";
import { ICoupon } from "../../models/ICoupon";
import CouponsService from "../../services/coupon.service";
import { useParams } from "react-router-dom";
import usePagination from "../usePagination/usePagination";
import { useDispatch } from "react-redux";
import { ActionType } from "../../redux/action-type";

export interface IUseCouponsProps {
    isAllowAll?: boolean;
    pageSize?: number;
}

function useCoupons<IUseCoupons>({ isAllowAll = true, pageSize = 4 }: IUseCouponsProps) {

    const [coupons, setCoupons] = useState<ICoupon[]>([]);
    const [companyIdFilter, setCompanyIdFilter] = useState<number>(0);
    const [maxPrice, setMaxPrice] = useState<number>(0);
    const [category, setCategory] = useState<string>('');
    const [searchedTitle, setSearchedTitle] = useState<string>('');
    const { companyId } = useParams();
    const id = companyId ? parseInt(companyId) : companyIdFilter;
    const [filter, setFilter] = useState<string>(id !== 0 ? 'BY_COMPANY' : '');
    const dispatch = useDispatch();

    const { totalElements, pageNumber, setPageNumber, setTotalElements } = usePagination({ pageSize: pageSize});

    const onCategoryChange = (category: string) => {
        if (category !== "") {
            setFilter("BY_CATEGORY")
            setCategory(category);
        } else {
            setFilter('');
        }
    }

    const onCompanyChange = (companyId: number) => {
        if (companyId !== 0) {
            setFilter("BY_COMPANY")
            setCompanyIdFilter(companyId);
        } else {
            setFilter('');
        }
    }

    const onMaxPriceChange = (maxPrice: number) => {
        setMaxPrice(maxPrice);
        setFilter("BY_MAX_PRICE");
    }

    const onDefaultFilter = () => {
        setMaxPrice(0);
        setCategory('');
        setCompanyIdFilter(0);
        setFilter('');
    }

    // ChangeEvent<HTMLInputElement>
    const onSearchChange = (e: any) => {
        if (e.target.value !== "") {
            setSearchedTitle(e.target.value);
            setFilter('SEARCH_BY_TITLE');
        } else {
            setSearchedTitle("");
            setFilter("");
        }
    }

    useEffect(() => {
        console.log(pageNumber, maxPrice, category, companyIdFilter, filter, searchedTitle);
        if (isAllowAll) {
            CouponsService.getAllCoupons(filter, pageNumber, pageSize, id, maxPrice, category, searchedTitle).then((response) => {
                setCoupons(response.data.content);
                setTotalElements(response.data.totalElements);
            });
        } else if (category || companyIdFilter) {
            CouponsService.getAllCoupons(filter, pageNumber, pageSize, companyIdFilter, maxPrice, category, searchedTitle).then((response) => {
                setCoupons(response.data.content);
                setTotalElements(response.data.totalElements);
            });
        }
    }, [filter, pageNumber, pageSize, companyIdFilter, maxPrice, category, searchedTitle]);

    return {
        coupons,
        pagination: { totalElements, pageSize, pageNumber, setPageNumber },
        filters: { onCategoryChange, onCompanyChange, onMaxPriceChange, onSearchChange, onDefaultFilter },
    }
}

export default useCoupons;
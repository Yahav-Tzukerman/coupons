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
    const [sortBy, setSortBy] = useState<string>('');
    const [isDescending, setDescending] = useState<boolean>(false);
    const { companyId } = useParams();
    const id = companyId ? parseInt(companyId) : companyIdFilter;
    const [filter, setFilter] = useState<string>(id !== 0 ? 'BY_COMPANY' : '');
    const dispatch = useDispatch();

    const { totalElements, pageNumber, setPageNumber, setTotalElements } = usePagination({ pageSize: pageSize });

    const onCategoryChange = (category: string) => {
        if (category !== "") {
            setFilter("BY_CATEGORY")
            setCategory(category);
            setPageNumber(0);
        } else {
            setFilter('');
            setPageNumber(0);
        }
    }

    const onCompanyChange = (companyId: number) => {
        if (companyId !== 0) {
            setFilter("BY_COMPANY")
            setCompanyIdFilter(companyId);
            setPageNumber(0);
        } else {
            setFilter('');
            setPageNumber(0);
        }
    }

    const onMaxPriceChange = (maxPrice: number) => {
        setMaxPrice(maxPrice);
        setFilter("BY_MAX_PRICE");
        setPageNumber(0);
    }

    const onDefaultFilter = () => {
        setMaxPrice(0);
        setCategory('');
        setCompanyIdFilter(0);
        setFilter('');
        setPageNumber(0);
    }

    // ChangeEvent<HTMLInputElement>
    const onSearchChange = (search: string) => {
        if (search !== "") {
            setSearchedTitle(search);
            setFilter('SEARCH_BY_TITLE');
            setPageNumber(0);
        } else {
            setSearchedTitle("");
            setFilter("");
            setPageNumber(0);
        }
    }

    useEffect(() => {
        console.log(pageNumber, maxPrice, category, companyIdFilter, filter, searchedTitle);
        if (isAllowAll) {
            CouponsService.getAllCoupons(filter, pageNumber, pageSize, id, maxPrice, category, searchedTitle, sortBy, isDescending).then((response) => {
                setCoupons(response.data.content);
                setTotalElements(response.data.totalElements);
            });
        } else if (category || companyIdFilter) {
            CouponsService.getAllCoupons(filter, pageNumber, pageSize, companyIdFilter, maxPrice, category, searchedTitle, sortBy, isDescending).then((response) => {
                setCoupons(response.data.content);
                setTotalElements(response.data.totalElements);
            });
        }
    }, [filter, pageNumber, pageSize, companyIdFilter, maxPrice, category, searchedTitle, sortBy, isDescending]);

    return {
        coupons,
        pagination: { totalElements, pageSize, pageNumber, setPageNumber },
        filters: {
            onCategoryChange, onCompanyChange, onMaxPriceChange, onSearchChange, onDefaultFilter, sortBy,
            isDescending,
            setDescending,
            setSortBy
        },
        setCoupons
    }
}

export default useCoupons;
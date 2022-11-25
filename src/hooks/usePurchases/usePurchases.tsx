import React, { useEffect, useState } from "react";
import { ICoupon } from "../../models/ICoupon";
import CouponsService from "../../services/coupon.service";
import { useParams } from "react-router-dom";
import { IPurchaseResponse } from "../../models/IPurchaseResponse";
import PurchaseService from '../../services/purchase.service';
import { useSelector } from "react-redux";
import { AppState } from "../../redux/app-state";
import usePagination from "../usePagination/usePagination";

export interface IUsePurchasesProps {
    isAllowAll?: boolean;
}

function usePurchases({ isAllowAll = true }: IUsePurchasesProps) {

    const [purchases, setPurchases] = useState<IPurchaseResponse[]>([]);
    const [total, setTotal] = useState<number>(0);
    const decodedToken = useSelector((state: AppState) => state.decodedToken);

    const { totalElements, pageSize, pageNumber, setPageNumber, setTotalElements, setPageSize } = usePagination();

    useEffect(() => {
        PurchaseService.getPurchasesByUserId(pageNumber, pageSize, decodedToken.userId).then((response) => {
            setPurchases(response.data.content);
            setTotalElements(response.data.totalElements);
        });
    }, [pageNumber]);

    useEffect(() => {
        PurchaseService.getPurchasesAmount().then((response) => {
            setTotal(response.data);
        });
    }, []);

    return {
        purchases,
        purchasesPagination: { totalElements, pageSize, pageNumber, setPageNumber, setPageSize },
        total
    }
}

export default usePurchases;
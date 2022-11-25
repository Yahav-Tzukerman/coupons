import React, { useEffect, useState } from "react";
import { ICompany } from '../../models/ICompany';
import CompanyService from '../../services/company.service';
import usePagination from "../usePagination/usePagination";



function useCompanies() {

    const [companies, setCompanies] = useState<ICompany[]>([]);
    const { totalElements, pageSize, pageNumber, setPageNumber, setTotalElements, setPageSize } = usePagination();

    function onDeleteCompany(id: number) {

        CompanyService.deleteCompany(id).then((response) => {
            alert(`coupon ${id} was deleted successfully!`)
        }).catch((error) => {
            alert(error.response.data.message);
        });
        window.location.reload();
    }

    useEffect(() => {
        CompanyService.getAllCompanies(pageNumber, pageSize).then((response) => {
            setCompanies(response.data.content);
            setTotalElements(response.data.totalElements);
        });
    }, [pageNumber]);

    return {
        companies,
        companiesPagination: { totalElements, pageSize, pageNumber, setPageNumber, setTotalElements, setPageSize },
        onDeleteCompany
    }
}

export default useCompanies;
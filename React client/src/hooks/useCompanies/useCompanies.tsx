import React, { useEffect, useState } from "react";
import { ICompany } from '../../models/ICompany';
import CompanyService from '../../services/company.service';
import usePagination from "../usePagination/usePagination";
import { confirmAlert } from 'react-confirm-alert';
import 'react-confirm-alert/src/react-confirm-alert.css';



function useCompanies() {

    const [companies, setCompanies] = useState<ICompany[]>([]);
    const { totalElements, pageSize, pageNumber, setPageNumber, setTotalElements } = usePagination({});

    function onDeleteCompany(id: number) {

        confirmAlert({
            title: 'Confirm to delete',
            message: 'Are you sure you want to delete this coupon?',
            buttons: [
                {
                    label: 'Yes',
                    onClick: () => {
                        CompanyService.deleteCompany(id).then((response) => {
                            alert(`coupon ${id} was deleted successfully!`)
                        }).catch((error) => {
                            alert(error.response.data.message);
                        });
                        window.location.reload();
                    }
                },
                {
                    label: 'No',
                }
            ]
        });


    }

    useEffect(() => {
        CompanyService.getAllCompanies(pageNumber, pageSize).then((response) => {
            setCompanies(response.data.content);
            setTotalElements(response.data.totalElements);
        });
    }, [pageNumber]);

    return {
        companies,
        companiesPagination: { totalElements, pageSize, pageNumber, setPageNumber, setTotalElements },
        onDeleteCompany
    }
}

export default useCompanies;
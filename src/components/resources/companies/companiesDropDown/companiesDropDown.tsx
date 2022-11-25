import React, { useEffect, useState } from "react"
import { ICompanyList } from "../../../../models/ICompanyList";
import CompanyService from "../../../../services/company.service";
import './companiesDropDown.css';

export interface ICompaniesDropDownProps {
    selectedCompany: (company: number) => void;
    defaultCompanyId?: number;
}

const CompaniesDropDown = ({ selectedCompany, defaultCompanyId }: ICompaniesDropDownProps) => {

    const defaultCompanies: ICompanyList[] = defaultCompanyId ? [{ id: defaultCompanyId, name: "" }] : [{ id: 0, name: "" }];
    const [companies, setCompanies] = useState<ICompanyList[]>(defaultCompanies);

    useEffect(() => {
        CompanyService.getCompaniesList().then((response) => {
            let list: ICompanyList[] = response.data;
            let responseList: ICompanyList[] = [{ id: 0, name: '' }, ...list];
            setCompanies(responseList);
        });
    }, []);

    return (
        <div className="dropdown-container">
            <select name="dropdown"
                onChange={(e) => selectedCompany(parseInt(e.target.value))}
                defaultValue={companies.filter(company => company.id === defaultCompanyId).pop()?.name}>

                {companies.map(company => (
                    <option value={company.id} key={company.id} >
                        {company.name}
                    </option>
                ))}

            </select>
        </div>
    );
}

export default CompaniesDropDown
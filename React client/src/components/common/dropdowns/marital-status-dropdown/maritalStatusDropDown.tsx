import React, { useEffect, useState } from "react"
import '../../../resources/companies/companiesDropDown/companiesDropDown.css';

export interface IMaritalStatusDropDownProps {
    selectedMaritalStatus: (role: string) => void;
    defaultMaritalStatus?: string;
}

const MaritalStatusDropDown = ({ selectedMaritalStatus, defaultMaritalStatus }: IMaritalStatusDropDownProps) => {

    return (
        <div className="dropdown-container">
            <select name="dropdown"
                onChange={(e) => selectedMaritalStatus(e.target.value)}
                defaultValue={defaultMaritalStatus ? defaultMaritalStatus : "CUSTOMER"}>

                <option value="SINGLE" key="1">
                    SINGLE
                </option>

                <option value="MARRIED" key="2">
                    MARRIED
                </option>

                <option value="WIDOWED" key="3">
                    WIDOWED
                </option>

                <option value="DIVORCED" key="4">
                    DIVORCED
                </option>

                <option value="UNKNOWN" key="5">
                    UNKNOWN
                </option>


            </select>
        </div>
    );
}

export default MaritalStatusDropDown
import React, { useEffect, useState } from "react"
import '../../../resources/companies/companiesDropDown/companiesDropDown.css';

export interface IRolesDropDownProps {
    selectedRole: (role: string) => void;
    defaultRole?: string;
}

const RolesDropDown = ({ selectedRole, defaultRole }: IRolesDropDownProps) => {

    return (
        <div className="dropdown-container">
            <select name="dropdown"
                onChange={(e) => selectedRole(e.target.value)}
                defaultValue={defaultRole ? defaultRole : "CUSTOMER"}>

                <option value="ADMIN" key="1">
                    ADMIN
                </option>

                <option value="COMPANY" key="2">
                    COMPANY
                </option>

                <option value="CUSTOMER" key="3">
                    CUSTOMER
                </option>


            </select>
        </div>
    );
}

export default RolesDropDown
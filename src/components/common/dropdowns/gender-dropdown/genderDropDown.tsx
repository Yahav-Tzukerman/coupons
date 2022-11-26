import React, { useEffect, useState } from "react"
import '../../../resources/companies/companiesDropDown/companiesDropDown.css';

export interface IGenderDropDownProps {
    selectedGender: (role: string) => void;
    defaultGender?: string;
}

const GenderDropDown = ({ selectedGender, defaultGender }: IGenderDropDownProps) => {

    return (
        <div className="dropdown-container">
            <select name="dropdown"
                onChange={(e) => selectedGender(e.target.value)}
                defaultValue={defaultGender ? defaultGender : "UNKNOWN"}>

                <option value="MALE" key="1">
                    MALE
                </option>

                <option value="FEMALE" key="2">
                    FEMALE
                </option>

                <option value="TRANSGENDER" key="3">
                    TRANSGENDER
                </option>

                <option value="AGENDER" key="3">
                    AGENDER
                </option>

                <option value="UNKNOWN" key="3">
                    UNKNOWN
                </option>


            </select>
        </div>
    );
}

export default GenderDropDown
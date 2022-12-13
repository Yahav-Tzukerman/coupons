import React, { useEffect, useState } from "react"
import '../../../resources/companies/companiesDropDown/companiesDropDown.css';

export interface ISortDropDownProps {
    selectedSort: (sort: string) => void;
    defaultSort?: string;
}

const SortDropDown = ({ selectedSort, defaultSort }: ISortDropDownProps) => {

    return (
        <div className="dropdown-container">
            <select name="dropdown"
                onChange={(e) => selectedSort(e.target.value)}
                defaultValue={defaultSort ? defaultSort : "price"}>

                <option value="price" key="1">
                    Sort by
                </option>

                <option value="price" key="2">
                    Price
                </option>

                <option value="title" key="3">
                    Title
                </option>

                <option value="startDate" key="4">
                    start date
                </option>

                <option value="endDate" key="5">
                    end date
                </option>

                <option value="amount" key="6">
                    Amount
                </option>

            </select>
        </div>
    );
}

export default SortDropDown
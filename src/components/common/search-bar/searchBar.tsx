import React, { useEffect, useState } from "react"
import useCoupons from "../../../hooks/useCoupons/useCoupons";
import { ICoupon } from "../../../models/ICoupon";
import { IFilters } from '../../../models/IFIlters';
import './searchBar.css'

export interface ISearchBarProps {
    filters: IFilters;
}

const SearchBar = ({ filters }: ISearchBarProps) => {

    const [search, setSearch] = useState<string>("");

    return (
        <div className="search-bar">
            <input type="text" placeholder="search for coupons" className="search-bar-input" onChange={(e) => { setSearch(e.target.value) }} />
            <input type="button" className="search-bar-btn" value="search" onClick={() => {
                filters.onSearchChange(search);
            }} />
        </div>
    );
}

export default SearchBar
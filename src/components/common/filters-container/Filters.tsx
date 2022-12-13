import { Col, Pagination, Row } from "react-bootstrap";
import CategoriesDropDown from "../../resources/categories/categoriesDropDown/categoriesDropDown";
import CompaniesDropDown from "../../resources/companies/companiesDropDown/companiesDropDown";
import './filters.css';
import { useState } from 'react';
import { IFilters } from '../../../models/IFIlters';
import SortDropDown from "../dropdowns/sort-dropdown/sortDropdown";

export interface IFiltersProps {
    filters: IFilters
}

export default function Filters({ filters }: IFiltersProps) {

    const { onCategoryChange, onCompanyChange, onMaxPriceChange, onDefaultFilter, sortBy, setSortBy, isDescending, setDescending } = filters;
    const [show, setShow] = useState<string>('');

    return (
        <div className="filters-container">
            <span className="label">Filters</span>
            <Row>
                <Col sm={2}>
                    <input type="button" id="default" name="filters" value="reset filters" className="reset-btn" onClick={() => {
                        setShow('');
                        onDefaultFilter();
                    }} />
                </Col>
            

                <Col sm={2}>
                    <input type="number" placeholder="max price" className="max" onChange={(e) => {
                        let value = e.target.value;
                        if (value == '') {
                            onDefaultFilter();
                        } else {
                            let number = parseInt(value);
                            if (number > 0) {
                                onMaxPriceChange(number);
                            } else {
                                onDefaultFilter();
                            }
                        }
                    }}
                    />
                </Col>

                <Col sm={2}>
                    <div className={show === 'category' ? 'show-filter' : 'hide'}>
                        <CategoriesDropDown
                            selectedCategory={onCategoryChange} />
                    </div>
                </Col>

                <Col sm={2}>
                    <div className={show === 'company' ? 'show-filter' : 'hide'}>
                        <CompaniesDropDown
                            selectedCompany={onCompanyChange} />
                    </div>
                </Col>
                <Col sm={2}>
                    <SortDropDown
                        selectedSort={setSortBy}
                        defaultSort={sortBy}
                    />

                </Col>

                <Col sm={2}>
                    <input type="button" className="asc-desc-btn" value={isDescending ? "Ascending" : "Descending"} onClick={() => setDescending(!isDescending)} /> 
                </Col>
             
            </Row>
        </div>
    );
}

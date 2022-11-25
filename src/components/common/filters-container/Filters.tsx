import { Col, Pagination, Row } from "react-bootstrap";
import CategoriesDropDown from "../../resources/categories/categoriesDropDown/categoriesDropDown";
import CompaniesDropDown from "../../resources/companies/companiesDropDown/companiesDropDown";
import './filters.css';
import { useState } from 'react';
import { IFilters } from '../../../models/IFIlters';
import { useSelector } from "react-redux";
import { AppState } from "../../../redux/app-state";

export interface IFiltersProps {
    filters: IFilters
}

export default function Filters({ filters }: IFiltersProps) {

    const { onCategoryChange, onCompanyChange, onMaxPriceChange, onDefaultFilter, onSearchChange } = filters;
    const [show, setShow] = useState<string>('');

    return (
        <div className="filters-container">
            <span className="label">Filters</span>
            <Row>
                <Col sm={12}>
                    <input type="button" id="default" name="filters" value="reset filters" className="reset-btn" onClick={() => {
                        setShow('');
                        onDefaultFilter();
                    }} />
                </Col>
            </Row>
            <Row>
                <Col sm={12}>
                    <span className="label">by max price</span>
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
            </Row>

            <Row>
                <Col sm={12}>
                    <span className="label">category</span>
                    <div className={show === 'category' ? 'show-filter' : 'hide'}>
                        <CategoriesDropDown
                            selectedCategory={onCategoryChange} />
                    </div>
                </Col>
            </Row>
            <Row>
                <Col sm={12}>
                    <span className="label">company</span>
                    <div className={show === 'company' ? 'show-filter' : 'hide'}>
                        <CompaniesDropDown
                            selectedCompany={onCompanyChange} />
                    </div>
                </Col>
            </Row>
        </div>
    );
}

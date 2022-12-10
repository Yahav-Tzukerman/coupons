import { useEffect, useState } from "react";
import { Col, Row } from "react-bootstrap";
import { useDispatch, useSelector } from "react-redux";
import useCoupons from "../../../hooks/useCoupons/useCoupons";
import { ActionType } from "../../../redux/action-type";
import { AppState } from "../../../redux/app-state";
import SortDropDown from "../../common/dropdowns/sort-dropdown/sortDropdown";
import Filters from "../../common/filters-container/Filters";
import CardsContainer from "../../resources/coupons/cardsContainer/cardsContainer";
import "./couponsPage.css";
import { store } from '../../../redux/store';
import SearchBar from "../../common/search-bar/searchBar";


export default function CouponsPage() {
    // const appCoupons = useSelector((state: AppState) => state.coupons);
    // const decodedToken = useSelector((state: AppState) => state.decodedToken);
    // const dispatch = useDispatch();
    const {
        coupons,
        pagination,
        filters,
        sortBy,
        isDescending,
        setDescending,
        setSortBy
    } = useCoupons({ isAllowAll: true, pageSize: 8 });


    useEffect(() => {
        // filters.onDefaultFilter();
        console.log(coupons);

        // dispatch({ type: ActionType.AppCoupons, payload: { coupons } });

    }, []);

    return (
        <div className="all-coupons-page">
            <Row>
                <Col sm={12}>
                    <h1 className="main-heading">Coupons</h1>
                </Col>
                <Col sm={12}>
                    <SearchBar
                        filters={filters} />
                </Col>
                <Col sm={2}>
                    <div className="app-coupons-filters">
                        <Filters
                            filters={filters}
                        />
                    </div>
                    <SortDropDown
                        selectedSort={setSortBy}
                        defaultSort={sortBy}
                    />
                    <input type="button" value={isDescending ? "<" : ">"} onClick={() => setDescending(!isDescending)} />
                </Col>
                <Col sm={10}>
                    <div className="cards-container coupons">
                        <CardsContainer
                            coupons={coupons}
                            pagination={pagination}
                        />
                    </div>
                </Col>
            </Row>
        </div >
    );
}
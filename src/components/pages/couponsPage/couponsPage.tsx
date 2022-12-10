import { useEffect, useState } from "react";
import { Col, Row } from "react-bootstrap";
import { useSelector } from "react-redux";
import useCoupons from "../../../hooks/useCoupons/useCoupons";
import { AppState } from "../../../redux/app-state";
import SortDropDown from "../../common/dropdowns/sort-dropdown/sortDropdown";
import Filters from "../../common/filters-container/Filters";
import CardsContainer from "../../resources/coupons/cardsContainer/cardsContainer";
import "./couponsPage.css";
import SearchBar from "../../common/search-bar/searchBar";
import AppModal from "../../common/AppModal/AppModal";
import AddOrEditCoupon from "../../common/AppForms/addOrEditCoupon";


export default function CouponsPage() {
    const decodedToken = useSelector((state: AppState) => state.decodedToken);
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
        console.log(coupons);
    }, []);

    return (
        <div className="all-coupons-page">
            <Row>
                <Col sm={12}>
                    <h1 className="main-heading">Coupons</h1>
                </Col>
                <Col sm={12}>
                    <div className="search-bar-container">
                        <SearchBar
                            filters={filters} />
                    </div>
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
                    {(decodedToken?.roles === "ROLE_ADMIN" || decodedToken.roles === "ROLE_COMPANY") &&
                        <AppModal
                            title={"Add Coupon"}
                        >
                            <AddOrEditCoupon />
                        </AppModal>
                    }
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
import { useEffect } from "react";
import { Col, Row } from "react-bootstrap";
import { useSelector } from "react-redux";
import useCoupons from "../../../hooks/useCoupons/useCoupons";
import { AppState } from "../../../redux/app-state";
import Filters from "../../common/filters-container/Filters";
import CardsContainer from "../../resources/coupons/cardsContainer/cardsContainer";
import "./couponsPage.css";
import SearchBar from "../../common/search-bar/searchBar";
import AppModal from "../../common/AppModal/AppModal";
import AddOrEditCoupon from "../../common/AppForms/addOrEditCoupon";


export default function CouponsPage() {
    const decodedToken = useSelector((state: AppState) => state.decodedToken);

    const {
        coupons,
        pagination,
        filters
    } = useCoupons({ isAllowAll: true, pageSize: 10 });


    useEffect(() => {
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
                <Col sm={12}>
                    <div className="app-coupons-filters">
                        <Filters
                            filters={filters}
                        />
                    </div>

                    {(decodedToken?.roles === "ROLE_ADMIN" || decodedToken.roles === "ROLE_COMPANY") &&
                        <AppModal
                            title={"Add Coupon"}
                        >
                            <AddOrEditCoupon />
                        </AppModal>
                    }
                </Col>
                <Col sm={12}>
                    <div className="cards-container-coupons">
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
import { Row, Col } from "react-bootstrap";
import { useSelector } from "react-redux";
import { Link, useNavigate } from "react-router-dom";
import { useState } from "react";
import './companyCard.css';
import { ICompany } from "../../../../models/ICompany";
import { AppState } from "../../../../redux/app-state";


export interface ICompanyCardProps {
    data: ICompany;
    deleteCompany: (id: number) => void;
}

const CompanyCard = ({
    data,
    deleteCompany
}: ICompanyCardProps) => {

    const decodedToken = useSelector((state: AppState) => state.decodedToken);
    const navigate = useNavigate();
    const [isGrayed, setGrayed] = useState<boolean>(false);


    return (
        <div className="company-card">
            <Link to={`/company/${data.id}`} key={data.id}>
                <Row>
                    <Col sm={3} >
                        <div className="company-logo-container">
                            <h3 className="company-name-header">
                                {data.name}
                            </h3>
                        </div>
                    </Col>
                    <Col sm={6} >
                        <h1 className="company-card-heading">
                            <img className="company-logo" src={data.logo} alt={`${data.name}_logo`} />
                            {/* {data.name} */}
                        </h1>
                    </Col>
                    <Col sm={3} >
                        <div className="company-content">
                            {/* <h6>{`Address: ${data.address}`}</h6>
                            <h6>{`Phone: ${data.phone}`}</h6>
                            <h6>{`Since: ${data.foundationDate}`}</h6> */}
                        </div>
                    </Col>
                </Row>
            </Link>
        </div>
    );
};

export default CompanyCard;
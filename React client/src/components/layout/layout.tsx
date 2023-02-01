import { Col, Row } from "react-bootstrap";
import App from "../../App";
import Footer from "./footer/footer";
import Header from "./header/header";
import './layout.css'

function layout() {
    return (
        <div className="layout">

            <Row>
                <Header />
                <Col sm={12}>
                    <App />
                </Col>
                <Col sm={12}>
                    <Footer />
                </Col>
            </Row>
        </div>
    )
}

export default layout;

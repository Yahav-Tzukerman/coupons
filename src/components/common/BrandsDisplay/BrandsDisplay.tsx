import React from "react";
import './brands.css';
import { omnia, tomorrowland, umf, edc, defqon1, ushaia } from './BrandImport'

const BrandsDisplay = () => {
    return(
        <div className="brand">
            <div>
                <img src={omnia} />
            </div>
            <div>
                <img src={tomorrowland} />
            </div>
            <div>
                <img src={umf} />
            </div>
            <div>
                <img src={edc} />
            </div>
            <div>
                <img src={defqon1} />
            </div>
            <div>
                <img src={ushaia} />
            </div>
        </div>
    );
};

export default BrandsDisplay;
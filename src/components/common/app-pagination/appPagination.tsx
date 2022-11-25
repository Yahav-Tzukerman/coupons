import { useState } from "react";
import { Col, Pagination, Row } from "react-bootstrap";
import './appPagination.css'

export interface IAppPaginationProps {
    pagination: {
        totalElements: number;
        pageSize: number;
        pageNumber: number;
        setPageNumber: (pageNumber: number) => void;
    }
}

export default function AppPagination({ pagination }: IAppPaginationProps) {

    const { totalElements, pageSize, pageNumber, setPageNumber } = pagination;

    let items = [];
    for (let i = 1; i <= Math.ceil(totalElements / pageSize); i++) {
        items.push(
            <Pagination.Item
                key={i}
                active={i === pageNumber + 1}
                onClick={() => setPageNumber(i - 1)}
            >
                {i}
            </Pagination.Item>
        );
    }

    return (
        <div>
            <Pagination className="pagination">
                {totalElements > pageSize && items}
            </Pagination>
        </div>
    );
}

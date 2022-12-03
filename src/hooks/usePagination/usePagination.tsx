import React, { useEffect, useState } from "react";

export interface IUsePaginationProps {
    pageSize?: number;
}

function usePagination({ pageSize = 5}: IUsePaginationProps) {

    const [pageNumber, setPageNumber] = useState<number>(0);
    const [totalElements, setTotalElements] = useState<number>(0);

    return {
        totalElements,
        pageSize,
        pageNumber,
        setPageNumber,
        setTotalElements,
    }
}

export default usePagination;
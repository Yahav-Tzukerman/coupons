import React, { useEffect, useState } from "react";

function usePagination() {

    const [pageSize, setPageSize] = useState<number>(5);
    const [pageNumber, setPageNumber] = useState<number>(0);
    const [totalElements, setTotalElements] = useState<number>(0);

    return {
        totalElements,
        pageSize,
        pageNumber,
        setPageNumber,
        setTotalElements,
        setPageSize
    }
}

export default usePagination;
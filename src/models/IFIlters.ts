import { IAppPaginationProps } from '../components/common/app-pagination/appPagination';

export interface IFilters{
   
    onCategoryChange: (category: string) => void,
    onCompanyChange: (companyId: number) => void,
    onMaxPriceChange: (maxPrice: number) => void,
    onSearchChange: (searchedTitle: string) => void,
    onDefaultFilter: () => void
}

// onCategoryChange, onCompanyChange, onMaxPriceChange, onDefaultFilter, onSearchChange
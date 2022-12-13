import { IAppPaginationProps } from '../components/common/app-pagination/appPagination';

export interface IFilters{
    sortBy: string,
    isDescending: boolean,
    setSortBy: (sortBy: string) => void,
    setDescending: (isDescending: boolean) => void,
    onCategoryChange: (category: string) => void,
    onCompanyChange: (companyId: number) => void,
    onMaxPriceChange: (maxPrice: number) => void,
    onSearchChange: (searchedTitle: string) => void,
    onDefaultFilter: () => void
}
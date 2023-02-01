import { IAppPaginationProps } from '../components/common/app-pagination/appPagination';
import { ICoupon } from './ICoupon';
import { IFilters } from './IFIlters';

export interface IUseCoupons{
    coupons: ICoupon[],
    pagination: IAppPaginationProps,
    filters: IFilters,
    onDeleteCoupon: (couponId: number) => void

}
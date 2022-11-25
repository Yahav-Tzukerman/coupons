export interface ICoupon {
    id: number,
    code: string,
    title: string,
    description: string,
    startDate: string,
    endDate: string,
    category: string,
    amount: number,
    price: number,
    companyId: number,
    companyName: string,
    imageUrl: string
}
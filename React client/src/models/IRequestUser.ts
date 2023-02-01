export interface IRequestUser{
    id?: number,
    username: string,
    password: string,
    firstName: string,
    lastName: string,
    role: string,
    phone: string,
    companyId: number | null
}
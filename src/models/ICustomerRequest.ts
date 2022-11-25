import { IRequestUser } from './IRequestUser';

export interface ICustomerRequest{
    user: IRequestUser,
    address: string,
    amountOfChildren: number,
    birthDate: string,
    gender: string,
    maritalStatus: string
}

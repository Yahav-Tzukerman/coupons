export interface IUserResponse {
    id: number,
    username: string,
    firstName: string,
    lastName: string,
    companyId?: number,
    phone: string,
    joinDate: Date,
    role: string,
    token: string
}
export interface IDecodedToken {
    sub: string,
    roles: string,
    userId: number,
    companyId?: number,
    exp: number
}



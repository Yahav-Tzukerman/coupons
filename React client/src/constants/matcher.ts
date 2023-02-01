export const BASE_URL = "http://localhost:8080";

export const CODE_REGEX = /^[A-Z0-9]{2,16}$/;

export const NAME_REGEX = /^[A-Za-z0-9-_.\s]{2,30}$/;

export const EMAIL_REGEX = /^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$/;

export const PASSWORD_REGEX = /^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\S+$).{8,}$/;

export const PHONE_REGEX = /^[\+]?[(]?[0-9]{2,4}[)]?[-\s\.]?[0-9]{3}[-\s\.]?[0-9]{4,6}$/;
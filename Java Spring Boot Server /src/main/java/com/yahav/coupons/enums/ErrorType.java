package com.yahav.coupons.enums;

public enum ErrorType {

    GENERAL_ERROR(400, 601, "General error", true),
    FAILED_TO_GENERATE_ID(400, 602, "Failed to generate new id", false),
    LOGIN_FAILURE(400, 603, "Login failed please try again", false),
    USER_DOES_NOT_EXIST(404, 604, "User does not exist", false),
    PURCHASE_DOES_NOT_EXIST(404, 605, "Purchase does not exist", false),
    COMPANY_DOES_NOT_EXIST(404, 606, "Company does not exist", false),
    INVALID_USERNAME(401, 607, "Username is Invalid please enter a valid email address", false),
    INVALID_PASSWORD(401, 608, "Password must contain at least 8 characters, at least one Upper case letter, one number and one Lower case letter", false),
    INVALID_USER_TYPE(400, 609, "Cannot proceed with this user type", false),
    USERNAME_ALREADY_EXIST(400, 610, "Username already exist", false),
    USER_IS_SUSPENDED(401, 611, "To many attempts to login - user is suspended for 5 minutes", false),
    NO_PERMISSION_GRANTED(401, 612, "No permission granted", false),
    CUSTOMER_DOES_NOT_EXIST(404, 613, "Customer does not exist", false),
    PURCHASE_ALREADY_EXIST(404, 614, "Purchase already exist", false),
    INVALID_PHONE_NUMBER(400, 615, "Phone number is invalid", false),
    COMPANY_ALREADY_EXIST(400, 616, "Company already exist", false),
    INVALID_USER(400, 617, "Invalid user", false),
    COUPON_DOES_NOT_EXIST(404, 618, "Coupon does not exist", false),
    COUPON_ALREADY_EXIST(404, 619, "Coupon already exist", false),
    SQL_ERROR(400, 620, "Something went wrong while trying to execute SQL query", true),
    CREATION_ERROR(400, 621, "Creation error", false),
    DELETION_ERROR(400, 622, "Deletion error", false),
    INVALID_NAME(400, 623, "Please enter a valid name", false),
    UPDATE_ERROR(400, 624, "Update Error", false),
    GET_OBJECT_ERROR(404, 625, "Get object error", false),
    INVALID_DATE(400, 626, "Invalid date", false),
    INVALID_FILED(400, 627, "Invalid filed", false),
    INVALID_AMOUNT(400, 633, "Amount must be greater then 0", false),
    INVALID_COUPON_CODE(400, 628, "Code must contain only upper case letters and numbers, length 2-6 characters", false),
    INVALID_IMAGE(400, 629, "Image file is invalid", false),
    CUSTOMER_ALREADY_EXIST(400, 630, "Customer is already exist", false),
    CATEGORY_ALREADY_EXIST(400, 631, "Category already exist", false),
    CATEGORY_DOES_NOT_EXIST(404, 632, "Category does not exist", false),
    INVALID_END_DATE(404, 633, "End date cant be more then a year from now or before start date", false),
    INVALID_START_DATE(404, 634, "Start date cant be before today", false),
    INVALID_TITLE(404, 635, "Title must be between 2-30 characters", false),
    INVALID_DESCRIPTION(404, 636, "Description must between 2-250 characters", false),
    INVALID_PRICE(400, 637, "Price must be greater then 0", false),
    INVALID_AMOUNT_OF_KIDS(400, 638, "Amount of kids is Invalid", false),
    INVALID_ADDRESS(400, 639, "Invalid address", false),
    INVALID_BIRTHDATE(404, 640, "Invalid birthdate", false);

    private int htmlErrorCode;
    private int errorNumber;
    private String errorMessage;
    private boolean isShowStackTrace;

    ErrorType(int htmlErrorCode, int errorNumber, String errorMessage, boolean isShowStackTrace) {
        this.htmlErrorCode = htmlErrorCode;
        this.errorNumber = errorNumber;
        this.errorMessage = errorMessage;
        this.isShowStackTrace = isShowStackTrace;
    }

    ErrorType(int errorNumber, String errorMessage) {
        this.errorNumber = errorNumber;
        this.errorMessage = errorMessage;
    }

    ErrorType(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public int getErrorNumber() {
        return errorNumber;
    }

    public boolean isShowStackTrace() {
        return isShowStackTrace;
    }

    public int getHtmlErrorCode() {
        return htmlErrorCode;
    }
}

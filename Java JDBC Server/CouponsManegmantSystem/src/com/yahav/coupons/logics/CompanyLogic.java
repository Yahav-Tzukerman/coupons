package com.yahav.coupons.logics;

import com.yahav.coupons.beans.Company;
import com.yahav.coupons.dal.CompanyDal;
import com.yahav.coupons.enums.ErrorType;
import com.yahav.coupons.exceptions.ServerException;

import javax.print.DocFlavor;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CompanyLogic {
    private static final CompanyDal companyDal = new CompanyDal();
    private static final CouponLogic couponLogic = new CouponLogic();
    private static final PurchaseLogic purchaseLogic = new PurchaseLogic();
    private static final UserLogic userLogic = new UserLogic();

    public CompanyLogic() {
    }

    public int updateCompany(Company company) throws ServerException {
        try {
            company.setId(companyDal.getId(company.getName()));
            validateCompany(company);
            return companyDal.updateCompany(company);
        } catch (ServerException e) {
            throw new ServerException(e, ErrorType.UPDATE_ERROR, "Failed to update company: " + company.getName());
        }

    }

    public Company getCompany(String companyName) throws ServerException {
        int companyId = companyDal.getId(companyName);
        return getCompany(companyId);
    }

    public Company getCompany(int companyId) throws ServerException {
        try {
            return companyDal.getCompany(companyId);
        } catch (ServerException e) {
            throw new ServerException(e, ErrorType.GET_OBJECT_ERROR, "Failed to get company");
        }
    }

    public List<Company> getCompanies() throws ServerException {
        try {
            return companyDal.getCompanies();
        } catch (ServerException e) {
            throw new ServerException(e, ErrorType.GET_OBJECT_ERROR, "Failed to get all companies");
        }
    }

    public void clearContent() throws ServerException {
        try {
            companyDal.clearContent();
        } catch (ServerException e) {
            throw new ServerException(e, ErrorType.GENERAL_ERROR, "Failed to clear companies content");
        }
    }

    public int deleteCompany(String companyName) throws ServerException {
        int companyId = companyDal.getId(companyName);
        if (companyId == -1) {
            throw new ServerException(ErrorType.COMPANY_DOES_NOT_EXIST, "Company: " + companyName);
        }
        return deleteCompany(companyId);
    }

    public int deleteCompany(int companyId) throws ServerException {
        String companyName = "";
        try {
            Company company = getCompany(companyId);
            if(company == null){
                throw new ServerException(ErrorType.COMPANY_DOES_NOT_EXIST);
            }
            companyName = company.getName();
            purchaseLogic.deleteCompanyPurchases(companyName);
            couponLogic.deleteCompanyCoupons(companyName);
            userLogic.deleteCompanyUsers(companyName);
            return companyDal.deleteCompany(companyId);
        } catch (ServerException e) {
            throw new ServerException(e, ErrorType.DELETION_ERROR, "Failed to delete company: " + companyName);
        }
    }

    public int addCompany(Company company) throws ServerException {
        try {
            if (companyDal.isCompanyExists(company.getName())) {
                throw new ServerException(ErrorType.COMPANY_ALREADY_EXIST, "Company: " + company.getName());
            }
            validateCompany(company);
            return companyDal.addCompany(company);
        } catch (ServerException e) {
            throw new ServerException(e, ErrorType.CREATION_ERROR, "Failed to add " + company.getName());
        }
    }

    private void validateCompany(Company company) throws ServerException {
        if (!nameValidation(company.getName())) {
            throw new ServerException(ErrorType.INVALID_NAME, "Invalid company name");
        }
        if (!phoneValidation(company.getPhone())) {
            throw new ServerException(ErrorType.INVALID_PHONE_NUMBER);
        }
        if (company.getFoundationDate() != null && company.getFoundationDate().after(Date.valueOf(LocalDate.now()))) {
            throw new ServerException(ErrorType.INVALID_DATE, "Invalid foundation date");
        }
        if (!nameValidation(company.getCountry())) {
            throw new ServerException(ErrorType.INVALID_NAME, "Country is Invalid");
        }
        if (!nameValidation(company.getCity())) {
            throw new ServerException(ErrorType.INVALID_NAME, "City is Invalid");
        }
        if (!nameValidation(company.getAddress())) {
            throw new ServerException(ErrorType.INVALID_NAME, "Address is Invalid");
        }
    }

    private boolean nameValidation(String name) {
        String regex = "^[A-Za-z0-9-_.\\s]{2,30}+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    private boolean phoneValidation(String phone) {
        String regex = "^[\\+]?[(]?[0-9]{2,4}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }
}

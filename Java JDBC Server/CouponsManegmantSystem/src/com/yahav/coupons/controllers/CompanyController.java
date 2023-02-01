package com.yahav.coupons.controllers;

import com.yahav.coupons.beans.Company;
import com.yahav.coupons.enums.ErrorType;
import com.yahav.coupons.exceptions.ServerException;
import com.yahav.coupons.logics.CompanyLogic;

import java.util.ArrayList;
import java.util.List;

public class CompanyController {
    private static final CompanyLogic companyLogic = new CompanyLogic();

    public void updateCompany(Company company) {
        try {
            int result = companyLogic.updateCompany(company);
            if (result != 0) {
                System.out.println(company.getName() + ": Company was updated successfully");
            }
        } catch (ServerException e) {
            System.out.println(e.getMessage());
        }
    }

    public Company getCompany(String companyName) {
        Company company = null;
        try {
            company = companyLogic.getCompany(companyName);
            if(company == null){
                throw new ServerException(ErrorType.COMPANY_DOES_NOT_EXIST);
            }
        } catch (ServerException e) {
            System.out.println(e.getMessage());
        }
        return company;
    }

    public Company getCompany(int companyId) {
        Company company = null;
        try {
            company = companyLogic.getCompany(companyId);
            if(company == null){
                throw new ServerException(ErrorType.COMPANY_DOES_NOT_EXIST);
            }
        } catch (ServerException e) {
            System.out.println(e.getMessage());
        }
        return company;
    }

    public List<Company> getCompanies() {
        List<Company> companies = new ArrayList<>();
        try {
            companies = companyLogic.getCompanies();
        } catch (ServerException e) {
            System.out.println(e.getMessage());
        }
        return companies;
    }

    public void deleteCompany(Company company) {
        deleteCompany(company.getName());
    }

    public void deleteCompany(String companyName) {
        try {
            int result = companyLogic.deleteCompany(companyName);
            if (result != 0) {
                System.out.println(companyName + ": Company was deleted successfully");
            }
        } catch (ServerException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteCompany(int companyId) {
        try {
            int result = companyLogic.deleteCompany(companyId);
            if (result != 0) {
                System.out.println(companyId + ": Company was deleted successfully");
            }
        } catch (ServerException e) {
            System.out.println(e.getMessage());
        }
    }

    public Integer addCompany(Company company) {
        Integer companyId = null;
        try {
            companyId = companyLogic.addCompany(company);
            if (companyId > 0) {
                System.out.println(company.getName() + ": Company was added successfully");
            }
        } catch (ServerException e) {
            System.out.println(e.getMessage());
        }
        return companyId;
    }
}
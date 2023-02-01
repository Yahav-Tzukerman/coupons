package com.yahav.coupons.services;

import com.yahav.coupons.converters.CompanyEntityToCompanyModelConverter;
import com.yahav.coupons.converters.CompanyModelToCompanyEntityConverter;
import com.yahav.coupons.entities.CompanyEntity;
import com.yahav.coupons.enums.ErrorType;
import com.yahav.coupons.exceptions.ServerException;
import com.yahav.coupons.models.CompanyListModel;
import com.yahav.coupons.models.CompanyModel;
import com.yahav.coupons.repositories.CompanyRepository;
import com.yahav.coupons.validations.RegexValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyModelToCompanyEntityConverter companyModelToCompanyEntityConverter;
    private final CompanyEntityToCompanyModelConverter companyEntityToCompanyModelConverter;

    @Autowired
    public CompanyService(CompanyRepository companyRepository
            , @Lazy CompanyModelToCompanyEntityConverter companyModelToCompanyEntityConverter
            , @Lazy CompanyEntityToCompanyModelConverter companyEntityToCompanyModelConverter) {
        this.companyRepository = companyRepository;
        this.companyModelToCompanyEntityConverter = companyModelToCompanyEntityConverter;
        this.companyEntityToCompanyModelConverter = companyEntityToCompanyModelConverter;
    }

    //only Admin
    public CompanyModel addCompany(CompanyModel companyModel) throws ServerException {
        if (companyRepository.existsByName(companyModel.getName())) {
            throw new ServerException(ErrorType.COMPANY_ALREADY_EXIST);
        }
        return saveCompany(companyModel);
    }

    //only Admin
    public CompanyModel updateCompany(CompanyModel companyModel) throws ServerException {
        if (!companyRepository.existsById(companyModel.getId())) {
            throw new ServerException(ErrorType.COMPANY_DOES_NOT_EXIST);
        }
        return saveCompany(companyModel);
    }

    //only Admin
    private CompanyModel saveCompany(CompanyModel companyModel) throws ServerException {
        validateCompany(companyModel);
        CompanyEntity companyEntity = companyModelToCompanyEntityConverter.convert(companyModel);
        if (companyEntity == null) {
            throw new ServerException(ErrorType.GENERAL_ERROR);
        }
        companyEntity = companyRepository.save(companyEntity);
        return companyEntityToCompanyModelConverter.convert(companyEntity);
    }

    // public
    public CompanyModel getCompany(long id) {
        return companyRepository.getCompanyById(id);
    }

    // public
    public Page<CompanyModel> getCompanies(int pageSize, int pageNumber) {
        Page<CompanyEntity> companyEntityPage = companyRepository.findAll(Pageable.ofSize(pageSize).withPage(pageNumber));
        return companyEntityPage.map(companyEntityToCompanyModelConverter::convert);
    }

    // public
    public List<CompanyListModel> getCompaniesList() {
        return companyRepository.companiesList();
    }

    //Only Admin
    public void removeCompany(long id) {
        companyRepository.deleteById(id);
    }

    // Server use
    public CompanyEntity getCompanyEntity(long id) throws ServerException {
        Optional<CompanyEntity> companyEntityOptional = companyRepository.findById(id);
        if (companyEntityOptional.isEmpty()) {
            throw new ServerException(ErrorType.COMPANY_DOES_NOT_EXIST);
        }
        return companyEntityOptional.get();
    }

    private void validateCompany(CompanyModel companyModel) throws ServerException {
        if (!RegexValidations.nameValidation(companyModel.getName())) {
            throw new ServerException(ErrorType.INVALID_NAME, "Invalid company name");
        }
        if (!RegexValidations.phoneValidation(companyModel.getPhone())) {
            throw new ServerException(ErrorType.INVALID_PHONE_NUMBER);
        }
        if (companyModel.getFoundationDate() != null && companyModel.getFoundationDate().isAfter(LocalDate.now())) {
            throw new ServerException(ErrorType.INVALID_DATE, "Invalid foundation date");
        }
        if (!RegexValidations.nameValidation(companyModel.getAddress())) {
            throw new ServerException(ErrorType.INVALID_NAME, "Address is Invalid");
        }
    }
}

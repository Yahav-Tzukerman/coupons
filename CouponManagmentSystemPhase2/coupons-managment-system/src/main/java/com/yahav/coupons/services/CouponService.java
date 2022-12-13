package com.yahav.coupons.services;

import com.yahav.coupons.converters.CouponEntityToCouponModelConverter;
import com.yahav.coupons.converters.CouponModelToCouponEntityConverter;
import com.yahav.coupons.entities.CompanyEntity;
import com.yahav.coupons.entities.CouponEntity;
import com.yahav.coupons.enums.DataFilter;
import com.yahav.coupons.enums.ErrorType;
import com.yahav.coupons.exceptions.ServerException;
import com.yahav.coupons.models.CouponModel;
import com.yahav.coupons.repositories.CouponRepository;
import com.yahav.coupons.security.jwt.JwtUserValidator;
import com.yahav.coupons.validations.RegexValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CouponService {

    private final CouponRepository couponRepository;
    private final CategoryService categoryService;
    private final CompanyService companyService;
    private final JwtUserValidator jwtUserValidator;
    private final CouponModelToCouponEntityConverter couponModelToCouponEntityConverter;
    private final CouponEntityToCouponModelConverter couponEntityToCouponModelConverter;

    @Autowired
    public CouponService(CouponRepository couponRepository, @Lazy CategoryService categoryService, @Lazy CompanyService companyService
            , @Lazy JwtUserValidator jwtUserValidator, @Lazy CouponModelToCouponEntityConverter couponModelToCouponEntityConverter
            , @Lazy CouponEntityToCouponModelConverter couponEntityToCouponModelConverter) {
        this.couponRepository = couponRepository;
        this.categoryService = categoryService;
        this.companyService = companyService;
        this.jwtUserValidator = jwtUserValidator;
        this.couponModelToCouponEntityConverter = couponModelToCouponEntityConverter;
        this.couponEntityToCouponModelConverter = couponEntityToCouponModelConverter;
    }

    // Admin Or Validated Company
    public CouponModel addCoupon(CouponModel couponModel, HttpServletRequest request) throws ServerException {
        if (couponModel.getCode() == null || couponRepository.existsByCode(couponModel.getCode())) {
            throw new ServerException(ErrorType.COUPON_ALREADY_EXIST);
        }
        if (couponModel.getStartDate() == null || couponModel.getStartDate().isBefore(LocalDate.now())) {
            throw new ServerException(ErrorType.INVALID_START_DATE);
        }
        return saveCoupon(couponModel, request);
    }

    // Admin Or Validated Company
    public CouponModel updateCoupon(CouponModel couponModel, HttpServletRequest request) throws ServerException {
        Optional<CouponEntity> couponOptional = couponRepository.findById(couponModel.getId());
        if (couponOptional.isEmpty()) {
            throw new ServerException(ErrorType.COUPON_DOES_NOT_EXIST);
        }
        CouponEntity coupon = couponOptional.get();
        if (coupon.getCompany().getId() != couponModel.getCompanyId()) {
            throw new ServerException(ErrorType.NO_PERMISSION_GRANTED);
        }
        return saveCoupon(couponModel, request);
    }

    // Admin Or Validated Company
    private CouponModel saveCoupon(CouponModel couponModel, HttpServletRequest request) throws ServerException {
        validateCoupon(couponModel, request);
        CouponEntity couponEntity = couponModelToCouponEntityConverter.convert(couponModel);
        if (couponEntity == null) {
            throw new ServerException(ErrorType.GENERAL_ERROR);
        }
        couponEntity = couponRepository.save(couponEntity);
        return couponEntityToCouponModelConverter.convert(couponEntity);
    }

    // Public
    public Page<CouponModel> getCoupons(int pageSize, int pageNumber, String sortBy, Boolean isDescending) {
        Pageable pageable = getPageable(pageSize, pageNumber, sortBy, isDescending);
        Page<CouponEntity> couponEntityPage = couponRepository.findAll(pageable);
        return couponEntityPage.map(couponEntityToCouponModelConverter::convert);
    }

    // Public
    public Page<CouponModel> getSearchCoupons(String searchedTitle, int pageSize, int pageNumber) {
        Page<CouponEntity> couponEntityPage = couponRepository.searchByTitle(searchedTitle, Pageable.ofSize(pageSize).withPage(pageNumber));
        return couponEntityPage.map(couponEntityToCouponModelConverter::convert);
    }

    // Public
    public Page<CouponModel> getCompanyCoupons(long companyId, int pageSize, int pageNumber, String sortBy, Boolean isDescending) throws ServerException {
        Pageable pageable = getPageable(pageSize, pageNumber, sortBy, isDescending);
        Page<CouponEntity> couponEntityPage = couponRepository.findByCompany(companyService.getCompanyEntity(companyId), pageable);
        return couponEntityPage.map(couponEntityToCouponModelConverter::convert);
    }

    // Public
    public Page<CouponModel> getCouponsByMaxPrice(float maxPrice, int pageSize, int pageNumber, String sortBy, boolean isDescending) {
        Pageable pageable = getPageable(pageSize, pageNumber, sortBy, isDescending);
        Page<CouponEntity> couponEntityPage = couponRepository.findByMaxPrice(maxPrice, pageable);
        return couponEntityPage.map(couponEntityToCouponModelConverter::convert);
    }

    // Public
    public Page<CouponModel> getCouponsByCategory(String category, int pageSize, int pageNumber, String sortBy, boolean isDescending) {
        Pageable pageable = getPageable(pageSize, pageNumber, sortBy, isDescending);
        Page<CouponEntity> couponEntityPage = couponRepository.findByCategory(categoryService.getCategoryEntityByName(category), pageable);
        return couponEntityPage.map(couponEntityToCouponModelConverter::convert);
    }

    // Public
    public Page<CouponModel> getCouponsWithFilter(String filter, int pageSize, int pageNumber, int companyId, int maxPrice, String category, String searchedTitle, String sortBy, boolean isDescending) throws ServerException {
        if(filter.equals("")){
            return getCoupons(pageSize ,pageNumber, sortBy, isDescending);
        }
        DataFilter dataFilter = DataFilter.valueOf(filter);
        switch (dataFilter){
            case BY_COMPANY:
                return getCompanyCoupons(companyId, pageSize, pageNumber, sortBy, isDescending);
            case BY_CATEGORY:
                return getCouponsByCategory(category, pageSize, pageNumber, sortBy, isDescending);
            case BY_MAX_PRICE:
                return getCouponsByMaxPrice(maxPrice, pageSize, pageNumber, sortBy, isDescending);
            case SEARCH_BY_TITLE:
                return getSearchCoupons(searchedTitle, pageSize, pageNumber);
            default:
                return getCoupons(pageSize, pageNumber, sortBy, isDescending);
        }
    }

    // Internal Server Use
    public List<CouponEntity> getCouponEntitiesByCategory(long id) {
        return couponRepository.getCouponsByCategory(id);
    }

    // Internal Server Use
    public List<CouponEntity> getCompanyCouponsEntities(long companyId) throws ServerException {
        CompanyEntity companyEntity = companyService.getCompanyEntity(companyId);
        return couponRepository.findByCompany(companyEntity);
    }

    // Internal Server Use
    public List<CouponModel> getExpiredCoupons() {
        return couponRepository.getExpiredCoupons();
    }

    // Internal Server Use
    public CouponModel getCoupon(long id) throws ServerException {
        if (!couponRepository.existsById(id)) {
            throw new ServerException(ErrorType.COUPON_DOES_NOT_EXIST);
        }
        return getCouponRequest(id);
    }

    // Public
    public CouponModel getCouponRequest(long id) {
        return couponRepository.getCoupon(id);
    }

    // Internal Server Use
    public CouponEntity getCouponEntity(long id) throws ServerException {
        Optional<CouponEntity> optionalCouponEntity = couponRepository.findById(id);
        if (optionalCouponEntity.isEmpty()) {
            throw new ServerException(ErrorType.COUPON_DOES_NOT_EXIST);
        }
        return optionalCouponEntity.get();
    }

    // Internal Server Use
    public void decreaseAmount(long id, int amount, HttpServletRequest request) throws ServerException {
        CouponModel couponModel = getCoupon(id);
        couponModel.setAmount(couponModel.getAmount() - amount);
        updateCoupon(couponModel, request);
    }

    // Internal Server Use
    public void removeCoupon(long id) {
        couponRepository.deleteById(id);
    }

    // Admin Or Validated Company
    public void removeCouponRequest(long id, HttpServletRequest request) throws ServerException {
        CouponModel couponModel = getCoupon(id);
        jwtUserValidator.validateCompanyUser(couponModel, request);
        removeCoupon(id);
    }

    private Pageable getPageable(int pageSize, int pageNumber, String sortBy, Boolean isDescending) {
        Pageable pageable;
        if (isDescending) {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
        } else {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        }
        return pageable;
    }

    private void validateCoupon(CouponModel couponModel, HttpServletRequest request) throws ServerException {
        if(couponModel.getStartDate() == null){
            throw new ServerException(ErrorType.INVALID_START_DATE);
        }
        if(couponModel.getEndDate() == null){
            throw new ServerException(ErrorType.INVALID_END_DATE);
        }
        jwtUserValidator.validateCompanyUser(couponModel, request);
        if (!RegexValidations.codeValidation(couponModel.getCode())) {
            throw new ServerException(ErrorType.INVALID_COUPON_CODE);
        }
        if (couponModel.getTitle().length() < 2 || couponModel.getTitle().length() > 30) {
            throw new ServerException(ErrorType.INVALID_TITLE);
        }
        if (couponModel.getDescription().length() < 2 || couponModel.getDescription().length() > 250) {
            throw new ServerException(ErrorType.INVALID_DESCRIPTION);
        }
        if (couponModel.getEndDate().isBefore(couponModel.getStartDate())) {
            throw new ServerException(ErrorType.INVALID_END_DATE);
        }
        if (couponModel.getEndDate().isAfter(couponModel.getStartDate().plusYears(1))) {
            throw new ServerException(ErrorType.INVALID_END_DATE);
        }
        if (couponModel.getAmount() < 1) {
            throw new ServerException(ErrorType.INVALID_AMOUNT);
        }
        if (couponModel.getPrice() <= 0) {
            throw new ServerException(ErrorType.INVALID_PRICE);
        }
        if(couponModel.getCategory() == null || couponModel.getCategory().equals("")){
            throw new ServerException(ErrorType.CATEGORY_DOES_NOT_EXIST);
        }
        if(couponModel.getCompanyId() == 0){
            throw new ServerException(ErrorType.COMPANY_DOES_NOT_EXIST);
        }



//        if (!RegexValidations.imageFileValidation(couponModel.getImageUrl())) {
//            throw new ServerException(ErrorType.INVALID_IMAGE);
//        }
    }
}

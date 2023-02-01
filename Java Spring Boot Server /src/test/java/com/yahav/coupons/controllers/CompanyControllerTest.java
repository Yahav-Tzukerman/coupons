package com.yahav.coupons.controllers;

import com.yahav.coupons.exceptions.ServerException;
import com.yahav.coupons.models.CompanyModel;
import com.yahav.coupons.services.CompanyService;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;
import org.mockito.quality.Strictness;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.CoreMatchers.is;
import static org.assertj.core.api.BDDAssertions.then;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class CompanyControllerTest {

    @Rule
    public MockitoRule role = MockitoJUnit.rule().strictness(Strictness.STRICT_STUBS);

    @Mock
    private CompanyService companyService;

    @InjectMocks
    private final CompanyController companyController = new CompanyController();

    @Test
    public void givenNonExistingCompanyMock_WhenAddedSuccessfully_thenCheckIfAddedCompanyReturnsCompanyMockWithId() throws ServerException {
        //given
        final CompanyModel mockCompany = getNonExistingCompanyMock();
        //when
        when(companyService.addCompany(mockCompany)).thenReturn(getCompanyMockWithId());
        final CompanyModel actualCompany = companyController.addCompany(mockCompany);
        //then
        CompanyModel expectedCompany = getCompanyMockWithId();

        System.out.println(mockCompany);
        System.out.println(actualCompany);
        System.out.println(expectedCompany);

        assertThat(actualCompany, is(expectedCompany));
    }

    @Test
    public void getCompanies() {
    }

    @Test
    public void getCompany() {
    }

    @Test
    public void removeCompany() {
    }

    @Test
    public void updateCompany() {
    }

    private CompanyModel getNonExistingCompanyMock(){
        return CompanyModel.builder()
                            .name("Starshf")
                            .address("AddressMock")
                            .phone("0546261880")
                            .foundationDate(LocalDate.now())
                            .build();
    }

    private CompanyModel getCompanyMockWithId(){
        return CompanyModel.builder()
                            .id(1)
                            .name("Starshf")
                            .address("AddressMock")
                            .phone("0546261880")
                            .foundationDate(LocalDate.now())
                            .build();
    }
}
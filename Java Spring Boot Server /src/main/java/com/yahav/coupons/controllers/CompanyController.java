package com.yahav.coupons.controllers;

import com.yahav.coupons.exceptions.ServerException;
import com.yahav.coupons.models.CompanyListModel;
import com.yahav.coupons.models.CompanyModel;
import com.yahav.coupons.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    public CompanyController() {
    }

    @PostMapping
    public CompanyModel addCompany(@RequestBody CompanyModel companyModel) throws ServerException {
        return companyService.addCompany(companyModel);
    }

    @GetMapping
    public Page<CompanyModel> getCompanies(@RequestParam(defaultValue = "0") @Min(value = 0) int pageNumber
            , @RequestParam(defaultValue = "15") @Min(value = 1) @Max(value = 100) int pageSize) {
        return companyService.getCompanies(pageSize, pageNumber);
    }

    @GetMapping("/list")
    public List<CompanyListModel> getCompanies() {
        return companyService.getCompaniesList();
    }

    @GetMapping("/{id}")
    public CompanyModel getCompany(@PathVariable(name = "id") long id) throws Exception {
        return companyService.getCompany(id);
    }

    @DeleteMapping("/{id}")
    public void removeCompany(@PathVariable(name = "id") long id) {
        companyService.removeCompany(id);
    }

    @PutMapping
    public CompanyModel updateCompany(@RequestBody CompanyModel companyModel) throws Exception {
        return companyService.updateCompany(companyModel);
    }
}

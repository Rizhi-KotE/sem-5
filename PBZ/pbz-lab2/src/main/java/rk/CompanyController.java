package rk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import rk.activemodel.Companies;
import rk.activemodel.Company;
import rk.activemodel.Outlet;
import rk.dto.CompanyDto;

import java.util.HashMap;
import java.util.Set;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class CompanyController {

    @Autowired
    private Companies companies;

    @RequestMapping(value = "/", method = GET)
    public ModelAndView get() {
        return getCompanies();
    }

    @RequestMapping(value = "/company/create", method = GET)
    public ModelAndView getCreateForm() {
        return new ModelAndView("create_company")
                .addObject("dto", new CompanyDto());
    }

    @RequestMapping(value = "/companyName", method = GET)
    public ModelAndView getCompanies() {
        Set<Company> companies = this.companies.find();
        return new ModelAndView("companies")
                .addObject("companies", companies);
    }

    @RequestMapping(value = "/companyName/{id}")
    public ModelAndView getCompanyOutlets(@PathVariable long id) {
        Company company = companies.findCompany(id);
        Set<Outlet> outlets = company.findAllOutlets();
        return new ModelAndView("company-info")
                .addObject("companyName", company)
                .addObject("outlets", outlets);
    }

    @RequestMapping(value = "/company/create", method = POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ModelAndView createCompany(@ModelAttribute CompanyDto companyDto) {
        Company company = companies.createCompany(companyDto.companyName);
        return getCompanyOutlets(company.getId());
    }
}

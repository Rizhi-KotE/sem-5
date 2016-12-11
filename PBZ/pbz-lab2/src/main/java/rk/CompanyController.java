package rk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import rk.activemodel.*;
import rk.dto.CompanyDto;
import rk.dto.DateRange;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class CompanyController {

    @Autowired
    private Companies companies;

    @Autowired
    private IndividualMPDs mpDs;

    @RequestMapping(value = "/", method = GET)
    public ModelAndView get() {
        return getCompanies();
    }

    @RequestMapping(value = "/company/create", method = GET)
    public ModelAndView getCreateForm() {
        return new ModelAndView("create_company")
                .addObject("dto", new CompanyDto())
                .addObject("waterUsageTypes", WaterUsageType.getTypes());
    }

    @RequestMapping(value = "/company", method = GET)
    public ModelAndView getCompanies() {
        Set<Company> companies = this.companies.find();
        return new ModelAndView("companies")
                .addObject("companies", companies);
    }

    @RequestMapping(value = "/company/{id}/update", method = GET)
    public ModelAndView updateCompany(@PathVariable long id) {
        Company company = companies.findCompany(id);
        return new ModelAndView("update_company")
                .addObject("dto", new CompanyDto(company))
                .addObject("waterUsageTypes", WaterUsageType.getTypes());
    }


    @RequestMapping(value = "/company/{id}")
    public ModelAndView getCompanyOutlets(@PathVariable long id) {
        Company company = companies.findCompany(id);
        Set<Outlet> outlets = company.findAllOutlets();
        return new ModelAndView("company-info")
                .addObject("company", company)
                .addObject("outlets", outlets);
    }

    @RequestMapping(value = "/company/create", method = POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ModelAndView createCompany(@ModelAttribute CompanyDto companyDto) {
        Company company = companies.createCompany(companyDto);
        return new ModelAndView("redirect:/company/" + company.getId());
    }

    @RequestMapping(value = "/company/{id}", method = DELETE)
    public ModelAndView delete(@PathVariable long id) {
        companies.remove(id);
        return new ModelAndView("redirect:/company");
    }

    @RequestMapping(value = "/company/{id}/get_substances_to_outlet", method = GET)
    public ModelAndView getSubstancesToOutlet(@PathVariable long id){
        Company company = companies.findCompany(id);
        Map<Outlet, List<MeasurementIndividualMPD>> substancesGroupedByOutlet = company.getSubstancesGroupedByOutlet();
        return new ModelAndView("substances_to_outlet")
                .addObject("map", substancesGroupedByOutlet)
                .addObject("company", company);
    }

    @RequestMapping(value = "/company/{id}/timeRange", method = POST)
    public ModelAndView getMpdsByTime(@PathVariable long id, @ModelAttribute DateRange dateBegin){
        Set<MeasurementIndividualMPD> mpds = mpDs.findToCompany(id, dateBegin);
        return new ModelAndView("mpDsList")
                .addObject("mpDs", mpds);
    }
}

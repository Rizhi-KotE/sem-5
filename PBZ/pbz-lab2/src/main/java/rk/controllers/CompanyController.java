package rk.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import rk.model.Company;
import rk.repositories.CompanyRepository;

import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyController {
    @Autowired
    private CompanyRepository repository;

    @RequestMapping(method = RequestMethod.GET)
    List<Company> getCompanies(){
        return repository.findAll();
    }
}

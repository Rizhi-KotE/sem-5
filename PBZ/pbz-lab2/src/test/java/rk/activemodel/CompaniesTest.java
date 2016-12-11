package rk.activemodel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import rk.dto.CompanyDto;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static rk.activemodel.WaterUsageType.SingleUsage;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CompaniesTest {

    @Autowired
    Companies companies;

    @Before
    public void setUp() throws Exception {


    }

    @Test
    public void createCompany() throws Exception {
        Company company = companies.createCompany("companyName", SingleUsage);
        assertEquals("companyName", company.getName());
    }

    @Test
    public void persistCompanyAndCheckItExist() throws Exception {
        Company company = companies.createCompany("companyName", SingleUsage);
        Company loadedCompany = companies.findCompany(company.getId());
        assertEquals(company.getId(), loadedCompany.getId());
        assertEquals(company.getName(), loadedCompany.getName());
    }

    @Test
    public void findAllCompanies() throws Exception {
        int currentSize = companies.find().size();

        Company company1 = companies.createCompany("company1", SingleUsage);
        Company company2 = companies.createCompany("company2", SingleUsage);
        Set<Company> loadedCompany = companies.find();
        assertEquals(currentSize + 2, loadedCompany.size());
    }

    @Test
    public void removeCompany() throws Exception {
        int currentSize = companies.find().size();

        Company company1 = companies.createCompany("company1", SingleUsage);
        Company company2 = companies.createCompany("company2", SingleUsage);
        companies.remove(company1.getId());
        Set<Company> loadedCompany = companies.find();
        assertEquals(currentSize + 1, loadedCompany.size());
    }

    @Test
    public void findByOutlet() throws Exception {
        Company expect = companies.createCompany("companyName", SingleUsage);
        Outlet newOutlet = expect.createOutlet(0, 0, 0, 0, 0, 0, 0);
        Company result = companies.findCompany(newOutlet);

        assertEquals(expect, result);
    }

    @Test
    public void updateTest() throws Exception {

        Company expect = companies.createCompany("companyName", SingleUsage);
        CompanyDto companyDto = new CompanyDto();
        companyDto.companyName = "companyNameDto";
        companyDto.waterUsage = "single";
        expect.update(companyDto);
        Company result = companies.findCompany(expect.getId());

        assertEquals(companyDto.companyName, result.getName());
    }
}

package rk.activemodel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

import static org.junit.Assert.assertEquals;

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
    public void createCompany() throws Exception{
        Company company = companies.createCompany("company");
        assertEquals("company", company.getName());
    }

    @Test
    public void persistCompanyAndCheckItExist() throws Exception {
        Company company = companies.createCompany("company");
        Company loadedCompany = companies.findCompany(company.getId());
        assertEquals(company.getId(), loadedCompany.getId());
        assertEquals(company.getName(), loadedCompany.getName());
    }

    @Test
    public void findAllCompanies() throws Exception {
        Company company1 = companies.createCompany("company1");
        Company company2 = companies.createCompany("company2");
        Set<Company> loadedCompany = companies.find();
        assertEquals(2, loadedCompany.size());
    }

    @Test
    public void removeCompany() throws Exception {
        Company company1 = companies.createCompany("company1");
        Company company2 = companies.createCompany("company2");
        companies.remove(company1.getId());
        Set<Company> loadedCompany = companies.find();
        assertEquals(1, loadedCompany.size());
    }
}

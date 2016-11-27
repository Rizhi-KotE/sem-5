package rk.activemodel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CompanyTest {

    @Autowired
    private Companies companies;

    private double angle;
    private double waste;
    private double flowRate;
    private double diameter;
    private double depth;
    private double distanceToCoast;
    private Company company;

    @Before
    public void setUp() throws Exception {
        company = companies.createCompany("company");
    }

    @Test
    public void createdOutletShouldExists() throws Exception {
        Outlet outlet = company.createNewOutlet(diameter, flowRate, waste, angle, depth, distanceToCoast);
        assertNotNull(outlet);
    }

    @Test
    public void createdOutletShouldAvailableFromCompany() throws Exception {
        Outlet outlet = company.createNewOutlet(diameter, flowRate, waste, angle, depth, distanceToCoast);
        Outlet loadedOutlet = company.findOutlet(outlet.getId());
        assertNotNull(loadedOutlet);
        assertEquals(outlet, loadedOutlet);
    }

    @Test
    public void findAllOutletsOfCompany() throws Exception {
        Outlet outlet1 = company.createNewOutlet(diameter, flowRate, waste, angle, depth, distanceToCoast);
        Outlet outlet2 = company.createNewOutlet(diameter, flowRate, waste, angle, depth, distanceToCoast);
        Set<Outlet> outlets = company.findAllOutlets();
        assertEquals(2, outlets.size());
    }
}
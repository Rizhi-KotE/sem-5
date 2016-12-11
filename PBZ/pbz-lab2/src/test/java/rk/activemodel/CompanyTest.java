package rk.activemodel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

import static org.junit.Assert.*;
import static rk.activemodel.WaterUsageType.SingleUsage;


@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CompanyTest {
    private double distanceOnWater;

    @Autowired
    private Companies companies;

    private double angle;
    private double waste;
    private double flowRate;
    private double diameter;
    private double depth;
    private double distanceToCoast;
    private Company company;

    @Test
    public void findAllOutlets() throws Exception {
        Company comany1 = companies.createCompany("comany1", SingleUsage);
        Company company2 = companies.createCompany("company2", SingleUsage);
        comany1.createOutlet(0, 0, 0, 0, 0, 0, 0);
        company2.createOutlet(0, 0, 0, 0, 0, 0, 0);
        Set<Outlet> allOutlets = comany1.findAllOutlets();
        assertEquals(1, allOutlets.size());
    }

    @Before
    public void setUp() throws Exception {
        company = companies.createCompany("companyName", SingleUsage);
    }

    @Test
    public void createdOutletShouldExists() throws Exception {
        Outlet outlet = company.createOutlet(diameter, flowRate, waste, angle, depth, distanceToCoast, distanceOnWater);
        assertNotNull(outlet);
    }

    @Test
    public void createdOutletShouldAvailableFromCompany() throws Exception {
        Outlet outlet = company.createOutlet(diameter, flowRate, waste, angle, depth, distanceToCoast, distanceOnWater);
        Outlet loadedOutlet = company.findOutlet(outlet.getId());
        assertNotNull(loadedOutlet);
        assertEquals(outlet, loadedOutlet);
    }

    @Test
    public void findAllOutletsOfCompany() throws Exception {
        Outlet outlet1 = company.createOutlet(diameter, flowRate, waste, angle, depth, distanceToCoast, distanceOnWater);
        Outlet outlet2 = company.createOutlet(diameter, flowRate, waste, angle, depth, distanceToCoast, distanceOnWater);
        Set<Outlet> outlets = company.findOutlets();
        assertEquals(2, outlets.size());
    }

    @Test
    public void testOutletHasDistanceToCoast() throws Exception {
        Set<Company> companies = this.companies.find();
        long count = companies.stream().flatMap(company1 -> company1.findAllOutlets().stream())
                .peek(outlet -> assertNotEquals(0d, outlet.getDistanceToCoast(), 0))
                .count();
        assertNotEquals(0, count);
    }
}
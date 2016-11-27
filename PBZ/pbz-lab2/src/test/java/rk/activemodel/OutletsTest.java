package rk.activemodel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OutletsTest {

    @Autowired
    private Outlets outlets;

    private double angle;
    private double waste;
    private double flowRate;
    private double diameter;
    private double depth;
    private double distanceToCoast;

    @Test
    public void createNewOutlet() throws Exception {
        Outlet outlet = outlets.createNewOutlet(diameter, flowRate, waste, angle, depth, distanceToCoast);
        assertNotNull(outlet);
    }

    @Test
    public void findById() throws Exception {
        Outlet outlet = outlets.createNewOutlet(diameter, flowRate, waste, angle, depth, distanceToCoast);
        Outlet savedOutlet = outlets.find(outlet.getId());
        assertNotNull(savedOutlet);
        assertEquals(outlet.getId(), savedOutlet.getId());
    }
}
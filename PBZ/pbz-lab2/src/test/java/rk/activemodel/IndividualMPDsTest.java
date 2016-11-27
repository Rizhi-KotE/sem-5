package rk.activemodel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IndividualMPDsTest {

    @Autowired
    private IndividualMPDs mpDs;

    @Autowired
    private Outlets outlets;

    private Outlet outlet;
    private Substance substance;
    private Alignment alignment;
    private double backgroundConcentration;
    private double concentrationInEffluent;

    @Before
    public void setUp() throws Exception {
        Random random = new Random();
        outlet = outlets.createNewOutlet(random.nextDouble(),
                random.nextDouble(), random.nextDouble(),
                random.nextDouble(), random.nextDouble(),
                random.nextDouble());
        substance = new Substance(1L);
        alignment = new Alignment(2l);
    }

    @Test
    public void createMPD() throws Exception {
        IndividualMPD mpd = mpDs.createMPD(outlet, substance, alignment, backgroundConcentration, concentrationInEffluent);
        assertNotNull(mpd);
        assertNotEquals(0, mpd.getId());
    }

    @Test
    public void findMPD() throws Exception {
        IndividualMPD mpd = mpDs.createMPD(outlet, substance, alignment, backgroundConcentration, concentrationInEffluent);
        Set<IndividualMPD> mpdSet = mpDs.find(outlet);
        assertEquals(1, mpdSet.size());
    }
}
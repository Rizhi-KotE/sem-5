package rk.activemodel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class IndividualMPDTest {
    @Autowired
    private IndividualMPDs mpDs;

    @Autowired
    private Outlets outlets;

    @Autowired
    private Companies companies;

    @Autowired
    private Alignments alignments;

    @Autowired
    private Substances substances;

    private Outlet outlet;
    private Substance substance;
    private Alignment alignment;
    private double backgroundConcentration = 1;
    private double concentrationInEffluent = 2;
    private double waste = 3;
    private Date date = new Date(100);
    private double distanceOnWater = 11;
    private double diameter = 12;
    private double flowRate = 13;
    private double angle = 14;
    private double depth = 15;
    private double distanceToCoast = 16;
    private double alignmentDistanceOnWater = 17;


    @Before
    public void setUp() throws Exception {
        Company company = companies.createCompany("companyName");
        outlet = company.createOutlet(diameter, flowRate, waste, angle, depth, distanceToCoast, distanceOnWater);
        alignment = alignments.createAlignment(outlet, alignmentDistanceOnWater);
        substance = substances.createSubstance("Substance");
    }

    @Test
    public void testAllPropertiesNotNull() throws Exception {

        MeasurementIndividualMPD mpd = mpDs.createMPD(outlet, substance,
                alignment, backgroundConcentration,
                concentrationInEffluent,
                waste, date);
        assertNotEquals(0, mpd.getId());
        assertNotNull(mpd.getAngle());
        assertNotEquals(0, mpd.getBackgroundConcentration());
        assertNotEquals(0, mpd.getConcentrationInEffluent());
        assertNotEquals(0, mpd.getDate());
        assertNotEquals(0, mpd.getDepth());
        assertNotEquals(0, mpd.getDiameter());
        assertNotEquals(0, mpd.getFlowRate());
        assertNotEquals(0, mpd.getMPC());
        assertNotEquals(0, mpd.getWaste());
        assertNotEquals(0, mpd.getDistanceToCoast());
        assertNotEquals(0, mpd.getDistanceToAlignment());
        assertNotEquals(0, mpd.getNSC());
    }

    @Test
    public void testAllPropetriesAreLoaded() throws Exception {
        int firstSize = mpDs.find(outlet).size();
        mpDs.createMPD(outlet, substance,
                alignment, backgroundConcentration,
                concentrationInEffluent,
                waste, date);
        Set<MeasurementIndividualMPD> mpds = mpDs.find(outlet);
        assertEquals(firstSize + 1, mpds.size());
        MeasurementIndividualMPD mpd = mpds.stream().findFirst().get();
        assertNotEquals(0, mpd.getId());
        assertNotNull(mpd.getAngle());
        assertNotEquals(0, mpd.getBackgroundConcentration());
        assertNotEquals(0, mpd.getConcentrationInEffluent());
        assertNotEquals(0, mpd.getDate());
        assertNotEquals(0, mpd.getDepth());
        assertNotEquals(0, mpd.getDiameter());
        assertNotEquals(0, mpd.getFlowRate());
        assertNotEquals(0, mpd.getMPC());
        assertNotEquals(0, mpd.getWaste());
        assertNotEquals(0, mpd.getDistanceToCoast());
        assertNotEquals(0, mpd.getDistanceToAlignment());
        assertNotEquals(0, mpd.getNSC());
    }
}
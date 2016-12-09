package rk.activemodel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.util.Random;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IndividualMPDsTest {

    @Autowired
    private IndividualMPDs mpDs;

    @Autowired
    private Companies companies;

    private Outlet outlet;
    private Substance substance;
    private Alignment alignment;
    private double backgroundConcentration;
    private double concentrationInEffluent;
    private double nsc;
    private double waste;
    private Date date;

    @Autowired
    private Substances substances;

    @Autowired
    private Alignments alignments;

    @Before
    public void setUp() throws Exception {
        Company company = companies.createCompany("companyName");
        Random random = new Random();
        outlet = company.createOutlet(random.nextDouble(),
                random.nextDouble(), random.nextDouble(),
                random.nextDouble(), random.nextDouble(),
                random.nextDouble(), random.nextDouble());
        substance = substances.createSubstance("substance");
        alignment = alignments.createAlignment(outlet, random.nextDouble());
        date = new Date(random.nextLong());
    }

    @Test
    public void createMPD() throws Exception {
        MeasurementIndividualMPD mpd = mpDs.createMPD(outlet, substance, alignment, backgroundConcentration, concentrationInEffluent,
                waste, date);
        assertNotNull(mpd);
        assertNotEquals(0, mpd.getId());
    }

    @Test
    public void findMPD() throws Exception {
        int firstSeize = mpDs.find(outlet).size();
        MeasurementIndividualMPD mpd = mpDs.createMPD(outlet, substance, alignment, backgroundConcentration, concentrationInEffluent,
                waste, date);
        Set<MeasurementIndividualMPD> mpdSet = mpDs.find(outlet);
        assertEquals(firstSeize + 1, mpdSet.size());
    }
}
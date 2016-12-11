package rk.activemodel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertEquals;
import static rk.activemodel.WaterUsageType.SingleUsage;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlignmentsTest {

    @Autowired
    private Alignments alignments;

    @Autowired
    private Companies companies;

    @Test
    public void find() throws Exception {
        Company company = companies.createCompany("companyName", SingleUsage);
        Outlet outlet = company.createOutlet(1, 1, 1, 1, 1, 1, 1);
        Alignment alignment = alignments.createAlignment(outlet, 2);
        Alignment result = alignments.find(alignment.getId());

        assertEquals(alignment, result);
    }

}
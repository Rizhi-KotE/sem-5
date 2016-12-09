package rk.activemodel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SubstancesTest {

    @Autowired
    private Substances substances;

    @Test
    public void createSubstance() throws Exception {


    }

    @Test
    public void findSubstance() throws Exception {
        Substance expect = substances.createSubstance("substance");
        Substance result = substances.findSubstance(expect.getId());

        assertEquals(expect, result);
    }
}
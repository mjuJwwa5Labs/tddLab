package pl.sda.testexamples.Calculator;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(JUnitParamsRunner.class)
public class CalculatorParametrizedJParamsTest {
    @Test
    @Parameters({
            "1, 1, 2",
            "2, 2, 4"
    })
    public void testAdd(int numberA, int numberB, int expected) {
        assertEquals(expected, numberA + numberB);
    }
}

package pl.sda.testexamples.Calculator;

import junitparams.FileParameters;
import junitparams.JUnitParamsRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(JUnitParamsRunner.class)
    public class CalculatorTestParametrizedCsv {

    CalculatorImpl calculator;

    @Before
    public void setUp() {
        calculator = new CalculatorImpl();
    }

    @Test
    @FileParameters("src/test/resources/resources.csv")
    public void testAdd(int a, int b, int result) {
        assertEquals(result, calculator.add(a,b));
    }

}

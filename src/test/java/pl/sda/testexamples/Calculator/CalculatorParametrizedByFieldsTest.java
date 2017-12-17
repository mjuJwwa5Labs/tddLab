package pl.sda.testexamples.Calculator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(value = Parameterized.class)
public class CalculatorParametrizedByFieldsTest {


    @Parameterized.Parameter(value = 0)
    public int numberA;
    @Parameterized.Parameter(value = 1)
    public int numberB;
    @Parameterized.Parameter(value = 2)
    public int expected;

    @Parameterized.Parameters(name = "{index}: testAdd({0}+{1}) = {2}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {1, 1, 2},
                {2, 2, 4}
        });
    }

    @Test
    public void testAdd() {
        assertEquals(expected, numberA + numberB);
    }
}
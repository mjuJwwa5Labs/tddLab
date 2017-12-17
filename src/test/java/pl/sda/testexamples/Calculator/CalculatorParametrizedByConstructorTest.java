package pl.sda.testexamples.Calculator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(value = Parameterized.class)
public class CalculatorParametrizedByConstructorTest {

    private int numberA;
    private int numberB;
    private int expected;

    public CalculatorParametrizedByConstructorTest(int numberA, int numberB, int expected) {
        this.numberA = numberA;
        this.numberB = numberB;
        this.expected = expected;
    }

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

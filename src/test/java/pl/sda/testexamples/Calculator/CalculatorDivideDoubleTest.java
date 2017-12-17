package pl.sda.testexamples.Calculator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CalculatorDivideDoubleTest {

    private CalculatorImpl calculator;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        calculator = new CalculatorImpl();
    }

    @Test
    public void divideDoubleTest() {
        double a = 9.0;
        double b = 3.0;

        Assert.assertEquals("9/3=3",3,calculator.divideDouble(a,b),0.1);
    }

    @Test (expected = IllegalArgumentException.class)
    public void divideIntExceptionWithExpectedExceptionTest() {
      int a = 2;
      int b = 0;

      calculator.divide(2,0);

    }

    @Test
    public void divideIntIllegallStateExceptionTest() {
        int a = 2;
        int b = 0;

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("zero");
        calculator.divide(2,0);
    }

    @Test
    public void divideDoubleWithFirstArgumentNullExceptionsTest() {
        //given
        double a = 2;
        double b = 0;
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("null");

        calculator.divideDouble(null,2.0);
    }

    @Test
    public void divideDoubleWithSecondArgumentNullExceptionsTest() {
        //given
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("null");

        //when
        calculator.divideDouble(2.0,null);
    }

    @Test
    public void divideDoubleByZeroExceptionsTest() {
        //given
        double a = 2;
        double b = 0;
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("zero");

        //when
        calculator.divideDouble(2.0,0.0);
    }

}

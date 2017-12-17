package pl.sda.testexamples.Calculator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CalculatorTest {

    CalculatorImpl calculator;

    @Before
    public void setUp() {
        calculator = new CalculatorImpl();
    }


    @Test
    public void testAdd() {
        //given
        int a = 1;
        int b = 2;

        //when
        int c = calculator.add(a,b);

        //then
        Assert.assertEquals("1+2=3",3,c);
    }

    @Test
    public void testDeduct() {
        //given
        int a = 1;
        int b = 2;

        //when
        int c = calculator.deduct(a,b);

        //then
        Assert.assertEquals("1-2=-1",-1,c);
    }

    @Test
    public void testDivide() {
        //given
        int a = 4;
        int b = 2;

        //when
        int c = calculator.divide(a,b);

        //then
        Assert.assertEquals("4/2=2",2,c);
    }

    @Test
    public void testDivideDouble() {
        //given
        double a = 4;
        double b = 2;

        //when
        double c = 4/2;

        //then
        Assert.assertEquals("4/2=2.0",2.0,c,0.1);
    }

    @Test
    public void testMultiply() {
        //given
        int a = 3;
        int b = 2;

        //when
        int c = calculator.multiply(a,b);

        //then
        Assert.assertEquals("3*2=6",6,c);
    }

}

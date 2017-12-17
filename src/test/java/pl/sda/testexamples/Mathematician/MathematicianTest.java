package pl.sda.testexamples.Mathematician;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.sda.testexamples.Calculator.Calculator;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MathematicianTest {

    @Mock
    private Calculator calculatorMock;

    @InjectMocks
    private Mathematician mathematician;

    @Test
    public void testAddWithoutCalculator() {
        // given
        int a = 2;
        int b = 3;

        // when
        int result = mathematician.add(a, b);

        // then
        assertEquals(result, 5);
        verifyNoMoreInteractions(calculatorMock);
    }


    @Test
    public void testAddWithCalculatorValue100() {
        // given
        int a = 100;
        int b = 3;

        // when
        int result = mathematician.add(a, b);

        // then
        assertEquals(result, 103);
        verifyNoMoreInteractions(calculatorMock);
    }

    @Test
    public void testAddWithCalculatorValue100_2() {
        // given
        int a = 105;
        int b = 3;
        when(calculatorMock.add(anyInt(), anyInt())).thenReturn(5);

        // when
        int result = mathematician.add(a, b);

        // then
        assertEquals(result, 5);
        verify(calculatorMock, times(1)).add(a, b);
        verifyNoMoreInteractions(calculatorMock);
    }

    @Test
    public void testAddWithCalculatorValue100_3() {
        // given
        int a = 101;
        int b = 3;
        when(calculatorMock.add(b, a)).thenReturn(104);

        // when
        int result = mathematician.add(b, a);

        // then
        assertEquals(result, 104);
        verify(calculatorMock, times(1)).add(b, a);
        verifyNoMoreInteractions(calculatorMock);
    }
}

package pl.sda.testexamples.Computer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ComputerTest {

    @Mock
    Display display;

    @Mock
    Keyboard keyboard;

    @InjectMocks
    private Computer computer;

    @Test
    public void testOperation() {
        //given
        when(keyboard.read()).thenReturn("Ala posiada kota");

        //when
        computer.operate();

        //then
        Mockito.verify(keyboard,times(1)).read();
        Mockito.verify(display,times(1)).display("Ala posiada kota");
        Mockito.verifyNoMoreInteractions(display,keyboard);

    }
}

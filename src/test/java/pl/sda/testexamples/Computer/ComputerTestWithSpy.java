package pl.sda.testexamples.Computer;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ComputerTestWithSpy {

    @Mock
    Display display;

    @Spy
    private Keyboard keyboard = new KeyboardImpl();

    @Captor
    private ArgumentCaptor<String> messageCaptor;

    @InjectMocks
    private Computer computer;

    @Test
    public void testOperationWhenProgrammed() {
        //given
        when(keyboard.read()).thenReturn("Ala ma kota");


        //when
        computer.operate();

        //then
        Mockito.verify(keyboard,times(1)).read();
        Mockito.verify(display,times(1)).display("Ala ma kota");
        Mockito.verifyNoMoreInteractions(display,keyboard);

    }

    @Test
    public void testOperationWhenNotProgrammed() {
        //given


        //when
        computer.operate();

        //then
        Mockito.verify(keyboard,times(1)).read();
        Mockito.verify(display,times(1)).display("Ala bez kota");
        Mockito.verifyNoMoreInteractions(display,keyboard);

    }

    @Test
    public void testOperationWithCaptor() {
        //given
        when(keyboard.read()).thenReturn("Ala ma kota");

        //when
        computer.operate();

        //then
        Mockito.verify(keyboard,times(1)).read();
        Mockito.verify(display,times(1)).display(messageCaptor.capture());
        Mockito.verifyNoMoreInteractions(display,keyboard);
        Assert.assertEquals("Ala ma kota",messageCaptor.getValue());
    }

}

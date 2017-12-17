package pl.sda.testexamples.Calculator;

import org.junit.Before;
import org.junit.Test;

import java.util.IllegalFormatException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

public class TokenizerAssertJTest {

    Tokenizer tokenizer;
    private List<String> listOfTokens;

    @Before
    public void setUp() {
        tokenizer = new Tokenizer();
    }

    @Test
    public void testNull() {
        //given
        String expression = null;

        //when
        listOfTokens = tokenizer.toTokens(expression);

        //then
        assertThat(listOfTokens).isEmpty();
    }

    @Test
    public void testIllegalNumberFormat() {
        //given
        String expression = ".";

        //when-then
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> tokenizer.toTokens(expression))
                .withMessageContaining("digit");
    }

    @Test
    public void testIllegalCharacterTest() {
        //given
        String expression = "/";

        //when-then
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> tokenizer.toTokens(expression))
                .withMessageContaining("not allowed");
    }

}

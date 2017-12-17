package pl.sda.testexamples.Calculator;

import junitparams.FileParameters;
import junitparams.JUnitParamsRunner;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(JUnitParamsRunner.class)
public class TokenizerTest {

    private Tokenizer tokenizer;
    private List<String> listOfTokens;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        tokenizer = new Tokenizer();
    }

    @Test
    public void splitMethodTest() {
        //given
        String testString = "1;(;);-;+;2;3;4;5;6;7;8;9;0";
        String[] testStringArray = testString.split(";");
        assertEquals("size should be 14",14,testStringArray.length);
    }

    @Test
    public void tokenizerEmptyStringTest() {
        //given
        String emptyString = "";

        //when
        listOfTokens = tokenizer.toTokens(emptyString);

        //then
        assertEquals("size should be 0",0,listOfTokens.size());
    }

    @Test
    public void tokenizerIgnoredCharactersSizeTest() {
        //given
        String ignoredCharacters = "(1\n+2\r+3\t+ 4)";

        //when
        listOfTokens = tokenizer.toTokens(ignoredCharacters);

        //then
        assertEquals("size should be 9",9,listOfTokens.size());
    }

    @Test
    public void checkIfNumberNoDigitTest() {
        //given
        String notNumberCharacters = ".";
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("digit");

        //then
        listOfTokens = tokenizer.toTokens(notNumberCharacters);
    }

    @Test
    public void tokenizerIgnoredCharactersLastCharacterTest() {
        //given
        String ignoredCharacters = "(1\n+2\r+3\t+ 4)";

        //when
        listOfTokens = tokenizer.toTokens(ignoredCharacters);

        //then
        assertEquals("size should be 9",")",listOfTokens.get(8));
    }

    @Test
    @FileParameters("src/test/resources/tokenizer/tokenizerSimpleMathSample.csv")
    public void toTokensBasicMathSizeTest(String expression, double firstArg, String operator, double secondArg, int expectedSize) {
        //when
        listOfTokens = tokenizer.toTokens(expression);

        //then
        assertEquals("size of tokens array",expectedSize, listOfTokens.size());
    }

    @Test
    @FileParameters("src/test/resources/tokenizer/tokenizerSimpleMathSample.csv")
    public void toTokensBasicMathArgumentsTest(String expression, double firstArg, String operator, double secondArg, int expectedSize) {
        //when
        listOfTokens = tokenizer.toTokens(expression);

        //then
        assertEquals("first argument",firstArg, Double.parseDouble(listOfTokens.get(0)),0.1);
        assertEquals("second argument",secondArg, Double.parseDouble(listOfTokens.get(2)),0.1);
    }

    @Test
    @FileParameters("src/test/resources/tokenizer/tokenizerSimpleMathSample.csv")
    public void toTokensBasicMathOperatorsTest(String expression, double firstArg, String operator, double secondArg, int expectedSize) {
        //when
        listOfTokens = tokenizer.toTokens(expression);

        //then
        assertEquals("operator",operator, listOfTokens.get(1));
    }

    @Test
    @FileParameters("src/test/resources/tokenizer/tokenizerWithBracketsSample.csv")
    public void toTokensComplexExpressionsSizeTest(String expression, String expectedExpression) {
        //given
        String[] expectedExpressionList = expectedExpression.split(";");

        //when
        listOfTokens = tokenizer.toTokens(expression);

        //then
        assertEquals("size of tokens array",expectedExpressionList.length, listOfTokens.size());
    }

    @Test
    @FileParameters("src/test/resources/tokenizer/tokenizerWithBracketsSample.csv")
    public void toTokensComplexExpressionsAllElementsTest(String expression, String expectedExpression) {
        //given
        String[] expectedExpressionList = expectedExpression.split(";");

        //when
        listOfTokens = tokenizer.toTokens(expression);

        //then
        assertEquals(Arrays.asList(expectedExpressionList),listOfTokens);
    }

    @Test
    @FileParameters("src/test/resources/tokenizer/tokenizerIllegalArgumentsSample.csv")
    public void toTokensIllegalArgumentsTest(String expression) {
        //given
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("is not allowed");

        //then
        listOfTokens = tokenizer.toTokens(expression);
    }

}

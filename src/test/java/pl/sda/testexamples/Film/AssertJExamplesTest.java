package pl.sda.testexamples.Film;

import org.assertj.core.data.Percentage;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.atIndex;

public class AssertJExamplesTest {

    @Test
    public void example1() {
        // JUnit
        Assert.assertEquals(4, 2 + 2);

        // AssertJ
        assertThat(2 + 2).isEqualTo(4);
    }

    @Test
    public void example2() {
        assertThat(2 + 2)
                .as("2+2=4").isEqualTo(4)
                .isNotEqualTo(5)
                .isLessThan(5)
                .isGreaterThan(3)
                .isBetween(0, 100)
                .isCloseTo(5, Percentage.withPercentage(25.0));
    }

    @Test
    public void example3() {
        assertThat(Arrays.asList(1,2,3)).contains(1, atIndex(0))
                .containsOnlyOnce(1, 2, 3);

        assertThat("Ala ma kota.").containsIgnoringCase("ala")
                .hasSize(12)
                .endsWith("kota.");

        assertThat(LocalDate.parse("2017-10-10")).isBefore("2017-10-11")
                .isAfterOrEqualTo("2017-10-10");

        FilmDTO film1 = FilmDTO.builder().title("Ala ma kota")
                .releaseDate(LocalDate.parse("2017-01-01")).build();
        FilmDTO film2 = FilmDTO.builder().title("Ala ma kota")
                .releaseDate(LocalDate.parse("2017-01-02")).build();

        assertThat(film1).isEqualToComparingOnlyGivenFields(film2, "title");
    }


}

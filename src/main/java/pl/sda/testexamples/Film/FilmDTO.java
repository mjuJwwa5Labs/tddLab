package pl.sda.testexamples.Film;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
public class FilmDTO {
    private String title;
    private LocalDate releaseDate;
    private Set<String> attributes;
}

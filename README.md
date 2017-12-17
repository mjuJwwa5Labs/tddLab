
# 1. Create project:

1.1. create mvn project, artifactId: tests-and-tdd
  
1.2. setup encoding and java compiler plugin
```
    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>
    
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.5.1</version>
                <configuration>
                    <source>1.8</source>
                    <target>1.8</target>
                </configuration>
            </plugin>
        </plugins>
    </build>
```
1.3. add junit 
```
    <dependencies>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
```

1.4. add coverage
```
            <plugin>
                <!-- http://www.eclemma.org/jacoco/trunk/doc/maven.html -->
                <groupId>org.jacoco</groupId>
                <artifactId>jacoco-maven-plugin</artifactId>
                <version>0.7.9</version>
                <executions>
                    <execution>
                        <id>default-prepare-agent</id>
                        <goals>
                            <goal>prepare-agent</goal>
                        </goals>
                    </execution>
                    <execution>
                        <id>default-report</id>
                        <goals>
                            <goal>report</goal>
                        </goals>
                    </execution>
                    <execution>
                        <id>default-check</id>
                        <goals>
                            <goal>check</goal>
                        </goals>

                        <configuration>
                            <rules>
                                <rule implementation="org.jacoco.maven.RuleConfiguration">
                                    <element>BUNDLE</element>
                                    <limits>
                                        <!-- współczynnik pokrycia -->
                                        <limit implementation="org.jacoco.report.check.Limit">
                                            <counter>INSTRUCTION</counter>
                                            <value>COVEREDRATIO</value>
                                            <minimum>0.10</minimum>
                                        </limit>
                                        <!--klasy bez testów-->
                                        <!--<limit implementation="org.jacoco.report.check.Limit">-->
                                        <!--<counter>CLASS</counter>-->
                                        <!--<value>MISSEDCOUNT</value>-->
                                        <!--<maximum>0</maximum>-->
                                        <!--</limit>-->
                                    </limits>
                                </rule>
                            </rules>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
```

# 2 First junit test

2.1. in test sources create package pl.sda.tdd.calculator

2.2. in the package create class CalculatorTest, implement adding test

```
public class CalculatorTest {

    @Test
    public void testAdd() {
        // given
        int a = 1;
        int b = 2;

        // when
        int c = a + b;

        // then
        Assert.assertEquals("1+2=3", c, 3);
    }
}
```

2.3. in main sources create package pl.sda.tdd.calculator, and create interface Caluclator

```
public class Calculator {

    int add(int a, int b);
}
```

and implementation CalculatorIml.

2.4. CalculatorTest add test related to Calculator class.

2.5. ADDITIONAL Add method to calculator interface, implementation to CalculatorImpl 
and suitable test in CalculatorTest

```
    Double add(Double a, Double b);
    Double divide(Double a, Double b);
```

for assertion with Double use: 
```
    Assert.assertEquals(double expected, double actual, double delta)
```

2.6. ADVANCED Please prepare tests for following class *(remember to add suitable dependencies in pom)*

```
public class Tokenizer {

    private static final Collection<Character> IGNORED_CHARS =
            Collections.unmodifiableCollection(Arrays.asList(' ', '\n', '\r', '\t'));

    private static final Collection<Character> SINGLE_CHAR_TOKENS =
            Collections.unmodifiableCollection(Arrays.asList('+', '-', '(', ')'));

    private static final Collection<Character> ALLOWED_CHARS =
            ImmutableList.<Character>builder()
                    .addAll(SINGLE_CHAR_TOKENS)
                    .add('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '.')
                    .build();

    public List<String> toTokens(String expression) {

        if (StringUtils.isBlank(expression)) {
            return Collections.emptyList();
        }

        List<String> tokens = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();

        expression.chars().mapToObj(c -> (char) c).forEach(c -> {

            if (IGNORED_CHARS.contains(c)) {
                return;
            }

            if (!ALLOWED_CHARS.contains(c)) {
                throw new IllegalArgumentException("char: " + c +
                        " is not allowed. Allowed characters are: " + ALLOWED_CHARS);
            }

            if (SINGLE_CHAR_TOKENS.contains(c)) {
                if (stringBuilder.length() != 0) {
                    tokens.add(checkIfNumber(stringBuilder));
                    stringBuilder.setLength(0);
                }
                tokens.add(String.valueOf(c));

            } else {
                stringBuilder.append(c);
            }
        });

        if (stringBuilder.length() != 0) {
            tokens.add(checkIfNumber(stringBuilder));
        }

        return tokens;
    }

    private String checkIfNumber(StringBuilder sb) {

        try {
            return String.valueOf(Double.parseDouble(sb.toString()));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Can not parse digit: " + sb, e);
        }
    }
}

```

test expressions:
- '1+8'
- '2.0+9.0'
- '(1/3.0)'
- '+/*'

# 3. testy parametryzowane:

```
public class CalculatorManuallyParametrizedTest {

    @Test  // metoda testowa sprawdzająca wiele przypadków
    public void addingNumbersPositiveNumbers() {
        assertAddingNumbers(1, 1, 2);
        assertAddingNumbers(0, 0, 0);
        assertAddingNumbers(-1, -1, -2);
    }

    // metoda zawierające logikę testu
    private void assertAddingNumbers(int first, int second, int expected) {
        assertEquals(expected, first + second);
    }
}
```

```
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
```

```
@RunWith(value = Parameterized.class)
public class CalculatorParametrizedByFieldsTest {

    @Parameterized.Parameter(value = 0)
    public int numberA;
    @Parameterized.Parameter(value = 1)
    public int numberB;
    @Parameterized.Parameter(value = 2)
    public int expected;

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
```

```
@RunWith(JUnitParamsRunner.class)
public class CalculatorParametrizedJParamsTest {

    @Test
    @Parameters({
            "1, 1, 2",
            "2, 2, 4"
    })
    public void testAdd(int numberA, int numberB, int expected) {
        assertEquals(expected, numberA + numberB);
    }
}
```

# 4. testowane wyjątków

```
public class ExceptionByAttributeTest {

    @Test(expected = IllegalStateException.class)
    public void testException(){
        throw new IllegalStateException("message");
    }
}
```

```
public class ExceptionManuallyTest {

    @Test
    public void testException() {
        try {
            throw new IllegalStateException("my message", new UnsupportedOperationException());
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalStateException);
            Assert.assertTrue(e.getMessage().contains("my"));
            Assert.assertTrue(e.getCause() instanceof  UnsupportedOperationException);
        }
    }
}
```

```
public class ExceptionWithRuleTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testException() {
        Throwable cause = new UnsupportedOperationException();
        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("my");
        expectedException.expectCause(is(cause));

        throw new IllegalStateException("my message", cause);
    }
}
```

# 5. AssertJ

5.1. general example
```
public class AssertJExamplesTest {

    @Test
    public void example1() {
        // JUnit
        Assert.assertEquals(4, 2 + 2);

        // AssertJ
        Assertions.assertThat(2 + 2).isEqualTo(4);
    }

    @Test
    public void example2() {
        Assertions.assertThat(2 + 2)
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
```

5.2. mapper example
```
@Data
@Builder
public class FilmDTO {

    private String title;
    private LocalDate releaseDate;
    private Set<String> attributes;
}
```

```
@Data
@Builder
public class FilmEntity {

    /**
     * Domyślny tytuł filmu w języku angielskim
     */
    private String defaultTitle;

    /**
     * Dostępne tłumaczenia tytułu.
     */
    private Map<String, String> titleInLanguages;

    /**
     * otwarty zbiór atrybutów. np 2d, 3d, imax, comedy, horror
     */
    private Set<String> attributes;

    /**
     * dzień premiery
     */
    private LocalDate releaseDate;
}
```

```
public class FilmMapper {

    public FilmDTO map(FilmEntity film, String languageCode) {
        Preconditions.checkArgument(film != null, "film can't be null");

        FilmDTO.FilmDTOBuilder builder = FilmDTO.builder()
                .releaseDate(film.getReleaseDate())
                .attributes(film.getAttributes());

        if (languageCode != null && film.getTitleInLanguages().keySet().contains(languageCode)) {
            builder.title(film.getTitleInLanguages().get(languageCode));
        } else {
            builder.title(film.getDefaultTitle());
        }
        return builder.build();
    }
}
```

```
public class FilmMapperTest {

    @Test
    public void mapperTest() {
        //given
        FilmEntity filmEntity = FilmEntity.builder()
                .defaultTitle("Star Wars")
                .attributes(Sets.newHashSet("2d", "imax"))
                .titleInLanguages(Collections.singletonMap("pl", "Gwiezdne Wojny"))
                .build();

        FilmMapper filmMapper = new FilmMapper();

        //when
        FilmDTO filmDTO = filmMapper.map(filmEntity, "pl");

        //then
        Assertions.assertThat(filmDTO)
                .hasFieldOrPropertyWithValue("title", "Gwiezdne Wojny")
                .hasFieldOrPropertyWithValue("releaseDate", null);
    }
}
```

5.3. dopisać test dla Tokenizer

# 6. mockito

6.1. running
```
public class MockitoManuallyTest {

    @Test
    public void noFilmIsReturned() {
        // given
        FilmRepository filmRepositoryMock = Mockito.mock(FilmRepository.class);
        when(filmRepositoryMock.filmsWithAttribute("2d")).thenReturn(Collections.emptySet());

        // when
        Set<FilmEntity> films = filmRepositoryMock.filmsWithAttribute("2d");

        // then
        Assertions.assertThat(films).isEmpty();
        Mockito.verify(filmRepositoryMock, times(1)).filmsWithAttribute("2d");
    }
}
```

```
public class MockitoRuleTest {
   
       @Rule
       public MockitoRule rule = new MockitoJUnit().rule();
       @Mock
       private FilmRepository filmRepositoryMock;
   
       @Test
       public void noFilmIsReturned() {
           // given
           when(filmRepositoryMock.filmsWithAttribute("2d")).thenReturn(Collections.emptySet());
   
           // when
           Set<FilmEntity> films = filmRepositoryMock.filmsWithAttribute("2d");
   
           // then
           Assertions.assertThat(films).isEmpty();
           Mockito.verify(filmRepositoryMock, times(1)).filmsWithAttribute("2d");
           Mockito.verifyNoMoreInteractions(filmRepositoryMock);
       }
   }
```

```
@RunWith(MockitoJUnitRunner.class)
public class MockitoRunnerTest {

    @Mock
    private FilmRepository filmRepositoryMock;

    @Test
    public void noFilmIsReturned() {
        // given
        when(filmRepositoryMock.filmsWithAttribute("2d")).thenReturn(Collections.emptySet());

        // when
        Set<FilmEntity> films = filmRepositoryMock.filmsWithAttribute("2d");

        // then
        Assertions.assertThat(films).isEmpty();
        Mockito.verify(filmRepositoryMock, times(1)).filmsWithAttribute("2d");
        Mockito.verifyNoMoreInteractions(filmRepositoryMock);
    }
}
```

6.2. Methematition

Jeżeli arkument jet większy od 100 trzeba użyć calculator


6.3. BookStore

- BooksStore
- PaymentService

# 7. TDD

7.1. Klasa sprawdzająca czy argument Integer jest parzysta
- dla 1
- dla 2
- dla -1
- dla null

7.2. Klasa konta (property balance)
- tworzenie 
- sprawdznie balance
- wplata
- pozyczka
- opłata za balance niższy od zera
- wyjątek jak przekroczy dopuszczalną wartośpożyczki

7.3. BookStore
- umowa na interface PaymentService i BookRepository, Book i BookDTO
- sprawdzenie argumentów
- udany zakup, sprawdzenie czy książka dosepna, pobranie sumy i dokonanie oplaty
- brak ksiązki
- zły numer kary
- brak pieniędzy

# 8. użyteczne zależności:
```
        <dependency>
            <groupId>com.google.guava</groupId>
            <artifactId>guava</artifactId>
            <version>23.4-jre</version>
        </dependency>

        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-lang3</artifactId>
            <version>3.7</version>
        </dependency>

        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>org.mockito</groupId>
            <artifactId>mockito-core</artifactId>
            <version>2.12.0</version>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>pl.pragmatists</groupId>
            <artifactId>JUnitParams</artifactId>
            <version>1.1.1</version>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>org.assertj</groupId>
            <artifactId>assertj-core</artifactId>
            <version>3.8.0</version>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>org.powermock</groupId>
            <artifactId>powermock-module-junit4</artifactId>
            <version>1.7.3</version>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.16.18</version>
            <scope>provided</scope>
        </dependency>

        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-email</artifactId>
            <version>1.2</version>
        </dependency>

```

# . lista zaklęć:
```
// buduje i odpala testy
mvn clean install
// buduje bez testów
mvn clean install -DskipTests
// buduje i odpala konkterny test
mvn clean install -Dtest=

// nowe repo
git init
// co zostało zmienione
git status
// listuje banche
git branch -a

// nowy branch
git checkout -b
// 
git commit 
// historia komitów
git log
// dodanie plików do cacha
git add .
// usunięcie z cache
git reset 
// pokacuje ostatni komit
git show
// pokazuje listę plików w ostatnim komicie
git show --name-only
```

.gitignore
```
# Intellij
.idea/
*.iml
*.iws

# Maven
target/

log
*.log

tmp
*.tmp
```

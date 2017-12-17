package pl.sda.testexamples.Calculator;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class Tokenizer {
    private static final Collection<Character> IGNORED_CHARS =
            Collections.unmodifiableCollection(Arrays.asList(' ', '\n', '\r', '\t'));

    private static final Collection<Character> SINGLE_CHAR_TOKENS =
            Collections.unmodifiableCollection(Arrays.asList('+', '-', '(', ')'));

    private static final Collection<Character> ALLOWED_CHARS =
            ImmutableList.<Character>builder()
                    .addAll(SINGLE_CHAR_TOKENS)
                    .add('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.')
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

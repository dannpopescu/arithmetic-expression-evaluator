package expressionevaluator.tokens;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class Tokenizer {

    private static final Set<Character> validCharacters =
            Set.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '-', '*', '/', '^', '(', ')', ' ');

    private Tokenizer() {
    }

    public static List<Token> tokenize(String string) {
        List<Token> tokens = new ArrayList<>();
        StringBuilder digitsAccumulator = new StringBuilder();

        for (char ch : string.toCharArray()) {
            if (Character.isDigit(ch)) {
                digitsAccumulator.append(ch);
                continue;
            }

            if (digitsAccumulator.length() > 0) {
                tokens.add(new RealNumber(digitsAccumulator.toString()));
                digitsAccumulator.setLength(0);
            }

            if (Operator.isOperator(ch)) {
                tokens.add(Operator.getInstance(ch));
            } else if (Parenthesis.isParenthesis(ch)) {
                tokens.add(Parenthesis.getInstance(ch));
            } else if (ch == ' ') {
                continue;
            } else {
                throw new IllegalArgumentException("Expression contains illegal characters: " + ch);
            }
        }

        if (digitsAccumulator.length() > 0) {
            tokens.add(new RealNumber(digitsAccumulator.toString()));
        }

        return tokens;
    }

    public static boolean isValidExpression(String expression) {
        for (char c : expression.toCharArray()) {
            if (!validCharacters.contains(c)) {
                return false;
            }
        }
        return true;
    }
}

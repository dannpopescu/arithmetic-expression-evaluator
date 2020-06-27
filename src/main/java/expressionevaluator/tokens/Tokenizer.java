package expressionevaluator.tokens;

import java.util.ArrayList;
import java.util.List;

public final class Tokenizer {

    private static Tokenizer instance;

    private Tokenizer() {
    }

    public static Tokenizer getInstance() {
        if (Tokenizer.instance == null) {
            Tokenizer.instance = new Tokenizer();
        }
        return Tokenizer.instance;
    }

    public List<Token> tokenize(String string) {
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
}

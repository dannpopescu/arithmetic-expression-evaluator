package expressionevaluator.tokens;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TokenizerTest {

    private Tokenizer tokenizer;

    @BeforeEach
    private void setUp() {
        tokenizer = Tokenizer.getInstance();
    }

    @Test
    @DisplayName("Only digits")
    void onlyDigits() {
        List<Token> tokens = tokenizer.tokenize("1 2 3");
        List<Token> expectedTokens = List.of(new RealNumber(1), new RealNumber(2), new RealNumber(3));

        assertEquals(expectedTokens, tokens);
    }

    @Test
    @DisplayName("Multiple-digit numbers")
    void multipleDigitNumbers() {
        List<Token> tokens = tokenizer.tokenize("111 222");
        List<Token> expectedTokens = List.of(new RealNumber(111), new RealNumber(222));

        assertEquals(expectedTokens, tokens);
    }

    @Test
    @DisplayName("Operators")
    void operators() {
        List<Token> tokens = tokenizer.tokenize("+ - */^");
        List<Token> expectedTokens = List.of(Operator.ADD, Operator.SUB, Operator.MUL, Operator.DIV, Operator.POW);

        assertEquals(expectedTokens, tokens);
    }

    @Test
    @DisplayName("Parentheses")
    void parentheses() {
        List<Token> tokens = tokenizer.tokenize("()");
        List<Token> expectedTokens = List.of(Parenthesis.LEFT, Parenthesis.RIGHT);

        assertEquals(expectedTokens, tokens);
    }

    @Test
    @DisplayName("Mixed Tokens")
    void mixedTokens() {
        List<Token> tokens = tokenizer.tokenize("(1+2)*33/5^10");
        List<Token> expectedTokens = List.of(Parenthesis.LEFT, new RealNumber(1), Operator.ADD,
                new RealNumber(2), Parenthesis.RIGHT, Operator.MUL, new RealNumber(33), Operator.DIV,
                new RealNumber(5), Operator.POW, new RealNumber(10));

        assertEquals(expectedTokens, tokens);
    }
}
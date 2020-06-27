package expressionevaluator.expression;

import expressionevaluator.tokens.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PostfixEvaluatorTest {

    private PostfixEvaluator evaluator;

    @BeforeEach
    private void setUp() {
        evaluator = new PostfixEvaluator();
    }

    @ParameterizedTest(name = "[{index}]  {0}   ==>   {1}")
    @DisplayName("Single operation")
    @CsvSource({
            "'5 3 +', 8",
            "'5 3 -', 2",
            "'5 3 *', 15",
            "'6 3 /', 2, ",
            "'5 3 ^', 125",
    })
    void singleOperation(@Tokenize List<Token> postfix, double expectedResult) {
        assertEquals(expectedResult, evaluator.evaluate(postfix));
    }

    @ParameterizedTest(name = "[{index}]  {0}   ==>   {1}")
    @DisplayName("One operation twice")
    @CsvSource({
            "'5 3 + 2 +', 10",
            "'5 3 - 2 -', 0",
            "'5 3 * 2 *', 30",
            "'6 3 / 2 /', 1",
            "'5 3 2 ^ ^', 1953125",
    })
    void sameOperationTwice(@Tokenize List<Token> postfix, double expectedResult) {
        assertEquals(expectedResult, evaluator.evaluate(postfix));
    }

    @ParameterizedTest(name = "[{index}]  {0}   ==>   {1}")
    @DisplayName("Two operations, different precedence")
    @CsvSource({
            "'5 3 2 * +', 11",
            "'5 3 * 2 -', 13",

            "'5 4 2 / -', 3",
            "'6 3 / 2 +', 4",

            "'5 3 2 ^ +', 14",
            "'5 3 ^ 2 -', 123",

            "'5 3 2 ^ *', 45",
            "'5 3 ^ 2 /', 62.5",
    })
    void twoOperationsDiffPrecedence(@Tokenize List<Token> postfix, double expectedResult) {
        assertEquals(expectedResult, evaluator.evaluate(postfix));
    }

    @ParameterizedTest(name = "[{index}]  {0}   ==>   {1}")
    @DisplayName("Complex expressions")
    @CsvSource({
            "'3 4 5 * 6 / +', 6.333333333333334",
            "'300 23 + 43 21 - * 84 7 + /', 78.08791208791209",
            "'4 8 + 6 5 - * 3 2 - 2 2 + * /', 3",
            "'3 2 1 + 2 3 2 ^ ^ * + 8 5 1 2 * 2 / - / -', 1537",
    })
    void complexExpressions(@Tokenize List<Token> postfix, double expectedResult) {
        assertEquals(expectedResult, evaluator.evaluate(postfix));
    }
}
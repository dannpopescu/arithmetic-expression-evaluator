package expressionevaluator.expression;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExpressionTest {

    private Expression exp;

    @ParameterizedTest(name = "[{index}]  {0}   ==>   {1}")
    @DisplayName("Complex expressions")
    @CsvSource({
            "3+4*5/6, 6.333333333333334",
            "(300+23)*(43-21)/(84+7), 78.08791208791209",
            "(4+8)*(6-5)/((3-2)*(2+2)), 3",
            "3+(2+1)*2^3^2-8/(5-1*2/2), 1537",
    })
    void complexExpressions(String mathExpression, double expectedResult) {
        exp = new Expression(mathExpression);
        assertEquals(expectedResult, exp.evaluate());
    }

}
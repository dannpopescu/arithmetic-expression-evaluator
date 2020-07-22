package com.danpopescu.arithmeticexpression.expression;

import com.danpopescu.arithmeticexpression.tokens.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InfixToPostfixConverterTest {

    private InfixToPostfixConverter converter;

    @BeforeEach
    private void setUp() {
        converter = new InfixToPostfixConverter();
    }

    @ParameterizedTest(name = "[{index}]  {0}   ==>   {1}")
    @DisplayName("Single operator")
    @CsvSource({
            "5+3, '5 3 +'",
            "5-3, '5 3 -'",
            "5*3, '5 3 *'",
            "5/3, '5 3 /'",
            "5^3, '5 3 ^'",
    })
    void singleOperation(@Tokenize List<Token> infix, @Tokenize List<Token> expectedPostfix) {
        assertEquals(expectedPostfix, converter.convert(infix));
    }

    @ParameterizedTest(name = "[{index}]  {0}   ==>   {1}")
    @DisplayName("Same operator twice")
    @CsvSource({
            "5+3+2, '5 3 + 2 +'",
            "5-3-2, '5 3 - 2 -'",
            "5*3*2, '5 3 * 2 *'",
            "5/3/2, '5 3 / 2 /'",
            "5^3^2, '5 3 2 ^ ^'",
    })
    void sameOperationTwice(@Tokenize List<Token> infix, @Tokenize List<Token> expectedPostfix) {
        assertEquals(expectedPostfix, converter.convert(infix));
    }

    @ParameterizedTest(name = "[{index}]  {0}   ==>   {1}")
    @DisplayName("Two operators, same precedence")
    @CsvSource({
            "5+3-2, '5 3 + 2 -'",
            "5-3+2, '5 3 - 2 +'",
            "5/3*2, '5 3 / 2 *'",
            "5*3/2, '5 3 * 2 /'",
    })
    void twoOperatorsSamePrecedence(@Tokenize List<Token> infix, @Tokenize List<Token> expectedPostfix) {
        assertEquals(expectedPostfix, converter.convert(infix));
    }

    @ParameterizedTest(name = "[{index}]  {0}   ==>   {1}")
    @DisplayName("Two operators, different precedence")
    @CsvSource({
            "5+3*2, '5 3 2 * +'",
            "5*3-2, '5 3 * 2 -'",

            "5-3/2, '5 3 2 / -'",
            "5/3+2, '5 3 / 2 +'",

            "5+3^2, '5 3 2 ^ +'",
            "5^3-2, '5 3 ^ 2 -'",

            "5*3^2, '5 3 2 ^ *'",
            "5^3/2, '5 3 ^ 2 /'",
    })
    void twoOperatorsDiffPrecedence(@Tokenize List<Token> infix, @Tokenize List<Token> expectedPostfix) {
        assertEquals(expectedPostfix, converter.convert(infix));
    }

    @ParameterizedTest(name = "[{index}]  {0}   ==>   {1}")
    @DisplayName("Single operator within parentheses")
    @CsvSource({
            "(5+3), '5 3 +'",
            "(5-3), '5 3 -'",
            "(5*3), '5 3 *'",
            "(5/3), '5 3 /'",
            "(5^3), '5 3 ^'"
    })
    void singleOperatorWithParentheses(@Tokenize List<Token> infix, @Tokenize List<Token> expectedPostfix) {
        assertEquals(expectedPostfix, converter.convert(infix));
    }

    @ParameterizedTest(name = "[{index}]  {0}   ==>   {1}")
    @DisplayName("Two operators, lower precedence inside parentheses")
    @CsvSource({
            "(5+3)*2, '5 3 + 2 *'",
            "5*(3-2), '5 3 2 - *'",

            "(5-3)/2, '5 3 - 2 / '",
            "5/(3+2), '5 3 2 + /'",

            "(5+3)^2, '5 3 + 2 ^'",
            "5^(3-2), '5 3 2 - ^'",

            "(5*3)^2, '5 3 * 2 ^'",
            "5^(3/2), '5 3 2 / ^'",
    })
    void twoOperatorsLowerPrecedenceInsideParentheses(@Tokenize List<Token> infix, @Tokenize List<Token> expectedPostfix) {
        assertEquals(expectedPostfix, converter.convert(infix));
    }

    @ParameterizedTest(name = "[{index}]  {0}   ==>   {1}")
    @DisplayName("Two operators, higher precedence inside parentheses")
    @CsvSource({
            "5+(3*2), '5 3 2 * +'",
            "(5*3)-2, '5 3 * 2 -'",

            "5-(3/2), '5 3 2 / -'",
            "(5/3)+2, '5 3 / 2 +'",

            "5+(3^2), '5 3 2 ^ +'",
            "(5^3)-2, '5 3 ^ 2 -'",

            "5*(3^2), '5 3 2 ^ *'",
            "(5^3)/2, '5 3 ^ 2 /'",
    })
    void twoOperatorsHigherPrecedenceInsideParentheses(@Tokenize List<Token> infix, @Tokenize List<Token> expectedPostfix) {
        assertEquals(expectedPostfix, converter.convert(infix));
    }

    @ParameterizedTest(name = "[{index}]  {0}   ==>   {1}")
    @DisplayName("Complex expressions")
    @CsvSource({
            "3+4*5/6, '3 4 5 * 6 / +'",
            "(300+23)*(43-21)/(84+7), '300 23 + 43 21 - * 84 7 + /'",
            "(4+8)*(6-5)/((3-2)*(2+2)), '4 8 + 6 5 - * 3 2 - 2 2 + * /'",
            "3+(2+1)*2^3^2-8/(5-1*2/2), '3 2 1 + 2 3 2 ^ ^ * + 8 5 1 2 * 2 / - / -'",
    })
    void complexExpressions(@Tokenize List<Token> infix, @Tokenize List<Token> expectedPostfix) {
        assertEquals(expectedPostfix, converter.convert(infix));
    }

    @ParameterizedTest
    @DisplayName("Exception: parentheses mismatch")
    @CsvSource({
            "(1+3",
            "1+3)",
            "(1+(3+5)",
            "1+(3+5))"
    })
    void parenthesesMismatch(@Tokenize List<Token> infix) {
        assertThrows(IllegalArgumentException.class, () -> converter.convert(infix));
    }
}
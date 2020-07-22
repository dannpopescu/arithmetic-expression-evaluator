package com.danpopescu.arithmeticexpression.tokens;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * An utility class that has the responsibility of converting arithmetic expressions
 * represented as String objects to a list of Token objects that are easier to
 * manipulate, convert and evaluate.
 */
public final class Tokenizer {

    public static final Set<Character> VALID_CHARACTERS =
            Set.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '-', '*', '/', '^', '(', ')', ' ');

    private Tokenizer() {
    }

    /**
     * Converts the arithmetic expression represented by the specified
     * String to a list of Token.
     *
     * @param arithmeticExpression an arithmetic expression
     * @return a list of Token objects that represent the specified expression
     * @throws IllegalArgumentException if the specified arithmetic expression
     *         contains illegal characters
     */
    public static List<Token> tokenize(String arithmeticExpression) {
        List<Token> tokens = new ArrayList<>();
        StringBuilder digitsAccumulator = new StringBuilder();

        for (char ch : arithmeticExpression.toCharArray()) {
            if (Character.isDigit(ch)) {
                digitsAccumulator.append(ch);
                continue;
            }

            if (digitsAccumulator.length() > 0) {
                tokens.add(new Operand(digitsAccumulator.toString()));
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
            tokens.add(new Operand(digitsAccumulator.toString()));
        }

        return tokens;
    }

    /**
     * Returns true if the specified string is a valid arithmetic expression.
     * More formally, returns true if and only if the specified String contains only
     * the characters in Tokenizer.VALID_CHARACTERS.
     *
     * @param arithmeticExpression an arithmetic expression
     * @return true if the specified string is a valid arithmetic expression
     */
    public static boolean isValidArithmeticExpression(String arithmeticExpression) {
        for (char c : arithmeticExpression.toCharArray()) {
            if (!Tokenizer.VALID_CHARACTERS.contains(c)) {
                return false;
            }
        }
        return true;
    }
}

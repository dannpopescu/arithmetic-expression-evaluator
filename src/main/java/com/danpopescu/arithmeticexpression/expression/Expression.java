package com.danpopescu.arithmeticexpression.expression;

import com.danpopescu.arithmeticexpression.tokens.*;

import java.util.Collections;
import java.util.List;

/**
 * An arithmetic expression.
 */
public class Expression {

    private final List<Token> infixForm;
    private List<Token> postfixForm;

    /**
     * Constructs an Expression from the the specified string.
     *
     * @param expression an arithmetic expression in form of a string
     * @throws IllegalArgumentException if the specified string is not a
     *         valid arithmetic expression
     */
    public Expression(String expression) {
        if (!Tokenizer.isValidArithmeticExpression(expression)) {
            throw new IllegalArgumentException(expression);
        }
        this.infixForm = Tokenizer.tokenize(expression);
    }

    /**
     * Generates the postfix form of this expression by rearranging the
     * tokens from the infix form.
     */
    private void generatePostfixForm() {
        var converter = new InfixToPostfixConverter();
        this.postfixForm = converter.convert(Collections.unmodifiableList(this.infixForm));
    }

    /**
     * Evaluates this expression.
     *
     * @return the result of this expression
     */
    public double evaluate() {
        if (this.postfixForm == null) {
            generatePostfixForm();
        }
        var evaluator = new PostfixEvaluator();
        return evaluator.evaluate(Collections.unmodifiableList(this.postfixForm));
    }

    /**
     * Converts a list to string by concatenating the toString of the elements
     * and using white spaces between them
     */
    private static <E> String listToString(List<E> list) {
        StringBuilder sb = new StringBuilder();
        for (E e : list) {
            sb.append(e).append(" ");
        }
        return list.isEmpty() ? "" : sb.substring(0, sb.length() - 1);
    }

    /**
     * Returns the postfix notation of this expression.
     *
     * @return the postfix notation of this expression
     */
    public String toPostfixString() {
        if (this.postfixForm == null) {
            generatePostfixForm();
        }
        return listToString(this.postfixForm);
    }

    /**
     * Returns the infix notation of this expression.
     *
     * @return the infix notation of this expression
     */
    public String toString() {
        return listToString(this.infixForm);
    }

}

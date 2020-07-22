package com.danpopescu.arithmeticexpression.expression;

import com.danpopescu.arithmeticexpression.tokens.Operand;
import com.danpopescu.arithmeticexpression.tokens.Operator;
import com.danpopescu.arithmeticexpression.tokens.Parenthesis;
import com.danpopescu.arithmeticexpression.tokens.Token;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * A converter that rearranges a list of tokens in infix order to postfix order.
 */
class InfixToPostfixConverter {

    private final List<Token> postfixTokens;
    private final Deque<Token> tokenStack;

    public InfixToPostfixConverter() {
        this.tokenStack = new ArrayDeque<>();
        this.postfixTokens = new ArrayList<>();
    }

    /**
     * Converts the arithmetic expression denoted by the specified
     * list of Tokens from infix to postfix form.
     *
     * @param infixTokens a list of Tokens arranged in the infix order
     * @return a list of Tokens arranged in the postfix form
     * @throws IllegalArgumentException if the arithmetic expression
     *         denoted by the specified list of Tokens is invalid.
     */
    public List<Token> convert(List<Token> infixTokens) {
        for (Token token : infixTokens) {
            if (token instanceof Operand) {
                this.postfixTokens.add(token);
            } else if (token instanceof Operator) {
                movePreviousOperatorsFromStackToPostfix((Operator) token);
                this.tokenStack.push(token);
            } else if (token == Parenthesis.LEFT) {
                this.tokenStack.push(token);
            } else if (token == Parenthesis.RIGHT) {
                moveOperatorsInsideParenthesisFromStackToPostfix();
                popLeftParenthesisFromStack();
            }
        }
        moveRemainingOperatorsFromStackToPostfix();

        return postfixTokens;
    }

    /**
     * Moves the previous operators that meet the conditions of precedence and associativity
     * from the stack to the list of tokens in postfix form.
     */
    private void movePreviousOperatorsFromStackToPostfix(Operator currentOperator) {
        while (!this.tokenStack.isEmpty() && this.tokenStack.peek() != Parenthesis.LEFT) {
            Operator prevOperator = (Operator) this.tokenStack.peek();
            if (currentOperator.hasLowerPrecedence(prevOperator)
                    || (currentOperator.hasEqualPrecedence(prevOperator) && prevOperator.hasLeftAssociativity())) {
                moveTopFromStackToPostfix();
            } else {
                return;
            }
        }
    }

    /**
     * Moves the top operator from the stack to the list of tokens in postfix form.
     */
    private boolean moveTopFromStackToPostfix() {
        return this.postfixTokens.add(this.tokenStack.pop());
    }

    /**
     * Moves the operators from the stack to the list of tokens in postfix form up to the
     * point when the stack is empty or a left parenthesis is met.
     */
    private void moveOperatorsInsideParenthesisFromStackToPostfix() {
        while (!this.tokenStack.isEmpty() && this.tokenStack.peek() != Parenthesis.LEFT) {
            moveTopFromStackToPostfix();
        }
    }

    /**
     * Pops the top element, that must be a left parenthesis, from the stack.
     */
    private void popLeftParenthesisFromStack() {
        if (this.tokenStack.peek() == Parenthesis.LEFT) {
            this.tokenStack.pop();
        } else {
            throw new IllegalArgumentException("Invalid expression. Parentheses don't match.");
        }
    }

    /**
     * Moves all the remaining operators in the stack to the list of tokens in postfix form.
     */
    private void moveRemainingOperatorsFromStackToPostfix() {
        while (!this.tokenStack.isEmpty()) {
            if (this.tokenStack.peek() == Parenthesis.LEFT) {
                throw new IllegalArgumentException("Invalid expression. Parentheses don't match.");
            }
            moveTopFromStackToPostfix();
        }
    }

}

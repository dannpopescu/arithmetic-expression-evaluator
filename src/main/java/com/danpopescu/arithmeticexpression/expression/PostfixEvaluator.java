package com.danpopescu.arithmeticexpression.expression;

import com.danpopescu.arithmeticexpression.tokens.Operand;
import com.danpopescu.arithmeticexpression.tokens.Operator;
import com.danpopescu.arithmeticexpression.tokens.Token;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

/**
 * An evaluator of postfix expressions.
 */
class PostfixEvaluator {

    private final Deque<Operand> numberStack;

    public PostfixEvaluator() {
        this.numberStack = new ArrayDeque<>();
    }

    /**
     * Evaluates the arithmetic expression denoted by the specified
     * list of Tokens arranged in postfix order.
     *
     * @param postfixTokens a list of Tokens in postfix order
     * @return the result of the specified arithmetic expression
     */
    public double evaluate(List<Token> postfixTokens) {
        for (Token token : postfixTokens) {
            if (token instanceof Operand) {
                this.numberStack.push((Operand) token);
            } else if (token instanceof Operator) {
                executeOperation((Operator) token);
            }
        }

        Operand result = this.numberStack.pop();

        if (!this.numberStack.isEmpty()) {
            throw new IllegalArgumentException("Invalid postfix expression");
        }

        return result.getValue();
    }

    /**
     * Executes the operation denoted by the specified operator on the top two
     * numbers from the stack.
     */
    private void executeOperation(Operator operator) {
        Operand op2 = this.numberStack.pop();
        Operand op1 = this.numberStack.pop();

        if (op1 == null || op2 == null) {
            throw new IllegalArgumentException("Invalid postfix expression");
        }

        Operand result = operator.execute(op1, op2);
        this.numberStack.push(result);
    }
}

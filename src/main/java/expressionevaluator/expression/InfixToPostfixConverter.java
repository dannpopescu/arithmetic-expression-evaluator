package expressionevaluator.expression;

import expressionevaluator.tokens.RealNumber;
import expressionevaluator.tokens.Operator;
import expressionevaluator.tokens.Parenthesis;
import expressionevaluator.tokens.Token;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

class InfixToPostfixConverter {

    private final List<Token> postfixTokens;
    private final Deque<Token> tokenStack;

    public InfixToPostfixConverter() {
        this.tokenStack = new ArrayDeque<>();
        this.postfixTokens = new ArrayList<>();
    }

    public List<Token> convert(List<Token> infixTokens) {
        for (Token token : infixTokens) {
            if (token instanceof RealNumber) {
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

    public void movePreviousOperatorsFromStackToPostfix(Operator currentOperator) {
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

    private boolean moveTopFromStackToPostfix() {
        return this.postfixTokens.add(this.tokenStack.pop());
    }

    private void moveOperatorsInsideParenthesisFromStackToPostfix() {
        while (!this.tokenStack.isEmpty() && this.tokenStack.peek() != Parenthesis.LEFT) {
            moveTopFromStackToPostfix();
        }
    }

    private void popLeftParenthesisFromStack() {
        if (this.tokenStack.peek() == Parenthesis.LEFT) {
            this.tokenStack.pop();
        } else {
            throw new IllegalArgumentException("Invalid expression. Parentheses don't match.");
        }
    }

    private void moveRemainingOperatorsFromStackToPostfix() {
        while (!this.tokenStack.isEmpty()) {
            if (this.tokenStack.peek() == Parenthesis.LEFT) {
                throw new IllegalArgumentException("Invalid expression. Parentheses don't match.");
            }
            moveTopFromStackToPostfix();
        }
    }

}

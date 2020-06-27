package expressionevaluator.expression;

import expressionevaluator.tokens.RealNumber;
import expressionevaluator.tokens.Operator;
import expressionevaluator.tokens.Token;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

class PostfixEvaluator {

    private final Deque<RealNumber> numberStack;

    public PostfixEvaluator() {
        this.numberStack = new ArrayDeque<>();
    }

    public double evaluate(List<Token> postfixTokens) {
        for (Token token : postfixTokens) {
            if (token instanceof RealNumber) {
                this.numberStack.push((RealNumber) token);
            } else if (token instanceof Operator) {
                executeOperation((Operator) token);
            }
        }

        RealNumber result = this.numberStack.pop();

        if (!this.numberStack.isEmpty()) {
            throw new IllegalArgumentException("Invalid postfix expression");
        }

        return result.getValue();
    }

    private void executeOperation(Operator operator) {
        RealNumber op2 = this.numberStack.pop();
        RealNumber op1 = this.numberStack.pop();

        if (op1 == null || op2 == null) {
            throw new IllegalArgumentException("Invalid postfix expression");
        }

        RealNumber result = operator.execute(op1, op2);
        this.numberStack.push(result);
    }
}

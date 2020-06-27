package expressionevaluator.expression;

import expressionevaluator.tokens.*;

import java.util.Collections;
import java.util.List;

public class Expression {

    private final List<Token> infixForm;
    private List<Token> postfixForm;

    public Expression(String expression) {
        this.infixForm = Tokenizer.getInstance().tokenize(expression);
    }

    private void generatePostfixForm() {
        var converter = new InfixToPostfixConverter();
        this.postfixForm = converter.convert(Collections.unmodifiableList(this.infixForm));
    }

    public double evaluate() {
        if (this.postfixForm == null) {
            generatePostfixForm();
        }
        var evaluator = new PostfixEvaluator();
        return evaluator.evaluate(Collections.unmodifiableList(this.postfixForm));
    }

    private static <E> String listToString(List<E> list) {
        StringBuilder sb = new StringBuilder();
        for (E e : list) {
            sb.append(e).append(" ");
        }
        return list.isEmpty() ? "" : sb.substring(0, sb.length() - 1);
    }

    public String toPostfixString() {
        if (this.postfixForm == null) {
            generatePostfixForm();
        }
        return listToString(this.postfixForm);
    }

    public String toString() {
        return listToString(this.infixForm);
    }

}

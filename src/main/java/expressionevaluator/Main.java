package expressionevaluator;

import expressionevaluator.expression.Expression;
import expressionevaluator.tokens.Tokenizer;

public class Main {

    public static void main(String[] args) {
        Tokenizer tok = Tokenizer.getInstance();
        System.out.println(tok.tokenize("1120 +30+ 02/5559^ 901"));
        System.out.println(tok.tokenize("1120 +30+ 02/5559^ 901"));
        Expression exp = new Expression("3+(2+1)*2^3^2-8/(5-1*2/2)");
        System.out.println(exp.toString());
        System.out.println(exp.toPostfixString());
        System.out.println(exp.evaluate());
    }
}

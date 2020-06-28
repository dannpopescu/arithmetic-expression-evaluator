package expressionevaluator;

import expressionevaluator.expression.Expression;
import expressionevaluator.tokens.Tokenizer;

import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("---Arithmetical Expression Evaluator---");
        System.out.println("Please enter an expression or 'q' to quit:");

        String input;
        while (true) {
            System.out.print(">>> ");
            input = scanner.nextLine();
            if (input.equals("q")) {
                System.out.println("Good bye...");
                break;
            }

            if (!Tokenizer.isValidArithmeticExpression(input)) {
                System.out.println("Invalid expression. Please try again.");
                continue;
            }

            Expression expression = new Expression(input);
            double result = expression.evaluate();
            if (result == Double.POSITIVE_INFINITY) {
                System.out.println("Division by zero. Please try again.");
            } else {
                System.out.println("Result: " + expression.evaluate());
            }
        }
    }

}
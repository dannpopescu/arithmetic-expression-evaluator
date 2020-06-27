package expressionevaluator.tokens;

import java.util.Set;

public enum Operator implements Token {
    ADD('+', 11, Operator.LEFT_ASSOCIATIVITY),
    SUB('-', 11, Operator.LEFT_ASSOCIATIVITY),
    MUL('*', 12, Operator.LEFT_ASSOCIATIVITY),
    DIV('/', 12, Operator.LEFT_ASSOCIATIVITY),
    POW('^', 13, Operator.RIGHT_ASSOCIATIVITY);

    private final char notation;
    private final int precedence;
    private final boolean associativity;

    private static final boolean LEFT_ASSOCIATIVITY = true;
    private static final boolean RIGHT_ASSOCIATIVITY = false;
    private static final Set<Character> OPERATORS_NOTATIONS = Set.of('+', '-', '*', '/', '^');

    Operator(char notation, int precedence, boolean associativity) {
        this.notation = notation;
        this.precedence = precedence;
        this.associativity = associativity;
    }

    public static boolean isOperator(char character) {
        return Operator.OPERATORS_NOTATIONS.contains(character);
    }

    public static Operator getInstance(char operatorChar) {
        switch (operatorChar) {
            case '+': return Operator.ADD;
            case '-': return Operator.SUB;
            case '*': return Operator.MUL;
            case '/': return Operator.DIV;
            case '^': return Operator.POW;
            default:
                throw new IllegalArgumentException(String.valueOf(operatorChar));
        }
    }

    public boolean hasLowerPrecedence(Operator operator) {
        return this.precedence < operator.precedence;
    }

    public boolean hasEqualPrecedence(Operator operator) {
        return this.precedence == operator.precedence;
    }

    public boolean hasLeftAssociativity() {
        return this.associativity == Operator.LEFT_ASSOCIATIVITY;
    }

    public RealNumber execute(RealNumber n1, RealNumber n2) {
        switch (this) {
            case ADD: return new RealNumber(n1.getValue() + n2.getValue());
            case SUB: return new RealNumber(n1.getValue() - n2.getValue());
            case MUL: return new RealNumber(n1.getValue() * n2.getValue());
            case DIV: return new RealNumber(n1.getValue() / n2.getValue());
            case POW: return new RealNumber(Math.pow(n1.getValue(), n2.getValue()));
            default:
                throw new IllegalStateException("New operators we're added to the Operator enum.");
        }
    }

    @Override
    public String toString() {
        return this.notation + "";
    }
}

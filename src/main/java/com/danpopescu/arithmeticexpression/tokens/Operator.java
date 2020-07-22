package com.danpopescu.arithmeticexpression.tokens;

import java.util.Set;

/**
 * Represents a mathematical operator.
 */
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

    /**
     * Checks if the specified char is a valid mathematical operator.
     *
     * @param character a character to checked
     * @return true if the specified char is a valid mathematical operator
     */
    public static boolean isOperator(char character) {
        return Operator.OPERATORS_NOTATIONS.contains(character);
    }

    /**
     * Returns the Operator instance that corresponds to the specified character,
     * that should be the notation of a valid mathematical operator.
     *
     * @param operatorChar the char notation of a valid mathematical operator
     * @return the Operator instance that corresponds to the specified character
     * @throws IllegalArgumentException if the specified operatorChar is not the
     *         notation of a valid mathematical operator
     */
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

    /**
     * Returns true if this operator has a lower precedence than the specified operator.
     *
     * @param operator the operator whose precedence to compare with the precedence of this instance
     * @return true if this operator has a lower precedence than the one passed as the parameter
     */
    public boolean hasLowerPrecedence(Operator operator) {
        return this.precedence < operator.precedence;
    }

    /**
     * Returns true if this operator has equal precedence with that of the specified operator.
     *
     * @param operator the operator whose precedence to compare with the precedence of this instance
     * @return true if this operator has equal precedence with that of the specified operator
     */
    public boolean hasEqualPrecedence(Operator operator) {
        return this.precedence == operator.precedence;
    }

    /**
     * Returns true if this operator has left associativity.
     *
     * @return true if this operator has left associativity.
     */
    public boolean hasLeftAssociativity() {
        return this.associativity == Operator.LEFT_ASSOCIATIVITY;
    }

    /**
     * Returns the result of executing the operation denoted by this operator
     * on the operands passed as parameters.
     *
     * @param operand1 the first operand
     * @param operand2 the second operand
     * @return the result of executing the operation denoted by this operator
     *         on the operands
     * @throws ArithmeticException if dividing by zero
     */
    public Operand execute(Operand operand1, Operand operand2) {
        switch (this) {
            case ADD: return new Operand(operand1.getValue() + operand2.getValue());
            case SUB: return new Operand(operand1.getValue() - operand2.getValue());
            case MUL: return new Operand(operand1.getValue() * operand2.getValue());
            case DIV:
                if (Double.compare(operand2.getValue(), 0) == 0) {
                    throw new ArithmeticException("Division by zero.");
                }
                return new Operand(operand1.getValue() / operand2.getValue());
            case POW: return new Operand(Math.pow(operand1.getValue(), operand2.getValue()));
            default:
                throw new IllegalStateException("New operators we're added to the Operator enum.");
        }
    }

    @Override
    public String toString() {
        return this.notation + "";
    }
}

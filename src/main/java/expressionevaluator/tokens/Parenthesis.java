package expressionevaluator.tokens;

/**
 * Represents a left or right parenthesis.
 */
public enum Parenthesis implements Token {
    LEFT('('),
    RIGHT(')');

    private final char notation;

    Parenthesis(char notation) {
        this.notation = notation;
    }

    /**
     * Returns true if the specified character is a parenthesis.
     *
     * @param character the character to be checked if it is a parenthesis
     * @return true if the specified character is a parenthesis
     */
    public static boolean isParenthesis(char character) {
        return character == '(' || character == ')';
    }

    /**
     * Returns the Parenthesis instance whose notation corresponds to the specified character.
     *
     * @param parenthesisChar the notation of a parenthesis
     * @return the Parenthesis instance whose notation corresponds to the specified character
     * @throws IllegalArgumentException if the specified character is not a parenthesis
     */
    public static Parenthesis getInstance(char parenthesisChar) {
        switch (parenthesisChar) {
            case '(': return Parenthesis.LEFT;
            case ')': return Parenthesis.RIGHT;
            default:
                throw new IllegalArgumentException(String.valueOf(parenthesisChar));
        }
    }

    @Override
    public String toString() {
        return notation + "";
    }
}

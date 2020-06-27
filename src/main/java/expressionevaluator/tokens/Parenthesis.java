package expressionevaluator.tokens;

public enum Parenthesis implements Token {
    LEFT('('),
    RIGHT(')');

    private final char notation;

    Parenthesis(char notation) {
        this.notation = notation;
    }

    public static boolean isParenthesis(char character) {
        return character == '(' || character == ')';
    }

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

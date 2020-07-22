package com.danpopescu.arithmeticexpression.tokens;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TokenizerTest {

    @Test
    @DisplayName("Only digits")
    void onlyDigits() {
        List<Token> tokens = Tokenizer.tokenize("1 2 3");
        List<Token> expectedTokens = List.of(new Operand(1), new Operand(2), new Operand(3));

        assertEquals(expectedTokens, tokens);
    }

    @Test
    @DisplayName("Multiple-digit numbers")
    void multipleDigitNumbers() {
        List<Token> tokens = Tokenizer.tokenize("111 222");
        List<Token> expectedTokens = List.of(new Operand(111), new Operand(222));

        assertEquals(expectedTokens, tokens);
    }

    @Test
    @DisplayName("Operators")
    void operators() {
        List<Token> tokens = Tokenizer.tokenize("+ - */^");
        List<Token> expectedTokens = List.of(Operator.ADD, Operator.SUB, Operator.MUL, Operator.DIV, Operator.POW);

        assertEquals(expectedTokens, tokens);
    }

    @Test
    @DisplayName("Parentheses")
    void parentheses() {
        List<Token> tokens = Tokenizer.tokenize("()");
        List<Token> expectedTokens = List.of(Parenthesis.LEFT, Parenthesis.RIGHT);

        assertEquals(expectedTokens, tokens);
    }

    @Test
    @DisplayName("Mixed Tokens")
    void mixedTokens() {
        List<Token> tokens = Tokenizer.tokenize("(1+2)*33/5^10");
        List<Token> expectedTokens = List.of(Parenthesis.LEFT, new Operand(1), Operator.ADD,
                new Operand(2), Parenthesis.RIGHT, Operator.MUL, new Operand(33), Operator.DIV,
                new Operand(5), Operator.POW, new Operand(10));

        assertEquals(expectedTokens, tokens);
    }
}
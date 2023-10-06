package com.craftinginterpreters.lox;

public class RuntimeError extends RuntimeException {
    final Token token;

    RuntimeError(Token token, String meesage) {
        super(meesage);
        this.token = token;
    }
}

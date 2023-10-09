package com.craftinginterpreters.lox;

import java.util.List;

public class LoxFunction implements LoxCallable {
    private final Stmt.Function declaration;
    private final Environment closure;

    LoxFunction(Stmt.Function declaration, Environment closure) {
        this.declaration = declaration;
        this.closure = closure;
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> arguments) {
        Environment environment = new Environment(closure);
        for (int i = 0; i < declaration.fun.params.size(); i++) {
            environment.define(
                declaration.fun.params.get(i).lexeme,
                arguments.get(i)
            );
        }
        try {
            interpreter.executeBlock(declaration.fun.body, environment);
        } catch (Return returnValue) {
            return returnValue.value;
        }
        return null;
    }

    @Override
    public int arity() {
        return declaration.fun.params.size();
    }

    @Override
    public String toString() {
        if (declaration.name.lexeme == null) {
            return "<lambda fn>";
        }
        return "<fn " + declaration.name.lexeme + ">";
    }
}

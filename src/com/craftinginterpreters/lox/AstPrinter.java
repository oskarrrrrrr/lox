package com.craftinginterpreters.lox;

public class AstPrinter implements Expr.Visitor<String>{
    String print(Expr expr) {
        return expr.accept(this);
    }

    @Override
    public String visitTernaryExpr(Expr.Ternary expr) {
        return parenthesize(
            expr.left_operator.lexeme + expr.right_operator.lexeme,
            expr.left,
            expr.middle,
            expr.right
        );
    }

    @Override
    public String visitThisExpr(Expr.This expr) {
        return "this";
    }

    @Override
    public String visitBinaryExpr(Expr.Binary expr) {
        return parenthesize(expr.operator.lexeme, expr.left, expr.right);
    }

    @Override
    public String visitGroupingExpr(Expr.Grouping expr) {
        return parenthesize("group", expr.expression);
    }

    @Override
    public String visitLiteralExpr(Expr.Literal expr) {
        if (expr.value == null) return "nil";
        return expr.value.toString();
    }

    @Override
    public String visitUnaryExpr(Expr.Unary expr) {
        return parenthesize(expr.operator.lexeme, expr.right);
    }

    @Override
    public String visitVariableExpr(Expr.Variable expr) {
        return expr.name.lexeme;
    }

    @Override
    public String visitLogicalExpr(Expr.Logical expr) {
        return parenthesize(expr.operator.lexeme, expr.left, expr.right);
    }

    @Override
    public String visitAssignExpr(Expr.Assign expr) {
        return "(= " + expr.name + expr.accept((this)) + ")";
    }

    @Override
    public String visitCallExpr(Expr.Call expr) {
        StringBuilder sb = new StringBuilder();
        sb.append(expr.callee.accept(this));
        sb.append("(");
        for (Expr argExpr : expr.arguments) {
            sb.append(argExpr.accept(this));
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public String visitFunctionExpr(Expr.Function expr) {
        return "<lambda>";
    }

    @Override
    public String visitGetExpr(Expr.Get expr) {
        return expr.object.accept(this) + "." + expr.name.lexeme;
    }

    @Override
    public String visitSetExpr(Expr.Set expr) {
        return expr.object.accept(this) + "." + expr.name.lexeme + " = " + expr.value.accept(this);
    }

    private String parenthesize(String name, Expr... exprs) {
        StringBuilder builder = new StringBuilder();
        builder.append("(").append(name);
        for (Expr expr : exprs) {
            builder.append(" ");
            builder.append(expr.accept(this));
        }
        builder.append(")");
        return builder.toString();
    }
}

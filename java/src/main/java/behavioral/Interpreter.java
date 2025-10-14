package behavioral;

import java.util.*;

interface Expr {

    boolean interpret(Map<String, Boolean> ctx);
}

class Var implements Expr {

    private final String name;

    Var(String name) {
        this.name = name;
    }

    @Override
    public boolean interpret(Map<String, Boolean> ctx) {
        return ctx.getOrDefault(name, false);
    }
}

class And implements Expr {

    private final Expr left, right;

    And(Expr l, Expr r) {
        this.left = l;
        this.right = r;
    }

    @Override
    public boolean interpret(Map<String, Boolean> ctx) {
        return left.interpret(ctx) && right.interpret(ctx);
    }
}

class Or implements Expr {

    private final Expr left, right;

    Or(Expr l, Expr r) {
        this.left = l;
        this.right = r;
    }

    @Override
    public boolean interpret(Map<String, Boolean> ctx) {
        return left.interpret(ctx) || right.interpret(ctx);
    }
}

class Not implements Expr {

    private final Expr inner;

    Not(Expr i) {
        this.inner = i;
    }

    @Override
    public boolean interpret(Map<String, Boolean> ctx) {
        return !inner.interpret(ctx);
    }
}

@SuppressWarnings("unused")
final class InterpreterExample {

    private InterpreterExample() {
    }

    public static void example() {
        Expr expr = new And(
                new Var("isAdmin"),
                new Or(new Var("featureEnabled"), new Not(new Var("maintenance")))
        );

        Map<String, Boolean> context = new HashMap<>();
        context.put("isAdmin", true);
        context.put("featureEnabled", false);
        context.put("maintenance", true);
        System.out.println("First evaluation: " + expr.interpret(context));

        context.put("featureEnabled", true);
        System.out.println("Second evaluation: " + expr.interpret(context));
    }
}

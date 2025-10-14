package behavioral;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

final class InterpreterTest {

    @Test
    void expressionEvaluatesAgainstContext() {
        Expr expr = new And(
            new Var("isAdmin"),
            new Or(new Var("featureEnabled"), new Not(new Var("maintenance")))
        );

        Map<String, Boolean> context = new HashMap<>();
        context.put("isAdmin", true);
        context.put("featureEnabled", false);
        context.put("maintenance", true);
        assertThat(expr.interpret(context)).isFalse();

        context.put("featureEnabled", true);
        assertThat(expr.interpret(context)).isTrue();
    }
}

package behavioral;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import org.junit.jupiter.api.Test;

final class TemplateMethodTest {

    @Test
    void teaAndCoffeeFollowTemplate() {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream original = System.out;
        System.setOut(new PrintStream(buffer, true));
        try {
            new Tea().prepareRecipe();
            new Coffee().prepareRecipe();
        } finally {
            System.setOut(original);
        }

        List<String> lines = buffer.toString().lines().toList();
        assertThat(lines).containsExactly(
            "Boiling water",
            "Steeping the tea",
            "Pouring into cup",
            "Adding lemon",
            "Boiling water",
            "Dripping coffee through filter",
            "Pouring into cup",
            "Adding sugar and milk"
        );
    }
}

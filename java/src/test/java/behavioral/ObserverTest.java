package behavioral;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import org.junit.jupiter.api.Test;

final class ObserverTest {

    @Test
    void stockNotifiesAllObserversOnPriceChange() {
        Stock stock = new Stock();
        stock.attach(new Chart());
        stock.attach(new Logger());

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream original = System.out;
        System.setOut(new PrintStream(buffer, true));
        try {
            stock.setPrice(100);
            stock.setPrice(120);
        } finally {
            System.setOut(original);
        }

        List<String> lines = buffer.toString().lines().toList();
        assertThat(lines).containsExactly(
            "Chart updated with price = 100",
            "Logger saved new price = 100",
            "Chart updated with price = 120",
            "Logger saved new price = 120"
        );
    }
}

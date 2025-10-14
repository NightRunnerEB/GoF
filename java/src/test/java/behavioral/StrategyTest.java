package behavioral;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

final class StrategyTest {

    @Test
    void paymentStrategiesProduceExpectedOutput() {
        PaymentService service = new PaymentService();

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream original = System.out;
        System.setOut(new PrintStream(buffer, true));
        try {
            service.setStrategy(new PayPalPayment());
            service.checkout(100);

            service.setStrategy(new CardPayment());
            service.checkout(200);

            service.setStrategy(new CryptoPayment());
            service.checkout(300);
        } finally {
            System.setOut(original);
        }

        assertThat(buffer.toString().trim())
            .isEqualTo(String.join(System.lineSeparator(),
                "Paid 100.0 via PayPal",
                "Paid 200.0 via Credit Card",
                "Paid 300.0 via Crypto"));
    }

    @Test
    void checkoutWithoutStrategyFails() {
        PaymentService service = new PaymentService();
        assertThatThrownBy(() -> service.checkout(42))
            .isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("No payment strategy");
    }
}

package behavioral;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import org.junit.jupiter.api.Test;

final class ChainOfResponsibilityTest {

    @Test
    void handlersPassRequestsAlongTheChain() {
        Handler auth = new AuthHandler();
        Handler log = new LoggerHandler();
        Handler business = new BusinessHandler();
        auth.setNext(log).setNext(business);

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream original = System.out;
        System.setOut(new PrintStream(buffer, true));
        try {
            auth.handle("auth request");
            auth.handle("normal request");
        } finally {
            System.setOut(original);
        }

        List<String> lines = buffer.toString().lines().toList();
        assertThat(lines).containsExactly(
            "AuthHandler: handled request",
            "Logger: normal request",
            "BusinessHandler: executing business logic"
        );
    }
}

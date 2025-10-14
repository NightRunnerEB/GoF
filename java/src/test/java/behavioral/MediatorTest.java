package behavioral;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import org.junit.jupiter.api.Test;

final class MediatorTest {

    @Test
    void chatMediatorBroadcastsMessagesToParticipants() {
        ChatMediator mediator = new ChatMediator();

        User alice = new ConcreteUser(mediator, "Alice");
        User bob = new ConcreteUser(mediator, "Bob");
        User charlie = new ConcreteUser(mediator, "Charlie");

        mediator.addUser(alice);
        mediator.addUser(bob);
        mediator.addUser(charlie);

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream original = System.out;
        System.setOut(new PrintStream(buffer, true));
        try {
            alice.send("Hi everyone!");
            bob.send("Hello Alice!");
        } finally {
            System.setOut(original);
        }

        List<String> lines = buffer.toString().lines().toList();
        assertThat(lines).containsExactly(
            "Alice sends: Hi everyone!",
            "Bob receives: Hi everyone!",
            "Charlie receives: Hi everyone!",
            "Bob sends: Hello Alice!",
            "Alice receives: Hello Alice!",
            "Charlie receives: Hello Alice!"
        );
    }
}

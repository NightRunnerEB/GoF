package behavioral;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import org.junit.jupiter.api.Test;

final class StateTest {

    @Test
    void atmTransitionsThroughStates() {
        ATM atm = new ATM();

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream original = System.out;
        System.setOut(new PrintStream(buffer, true));
        try {
            atm.withdraw();
            atm.insertCard();
            atm.enterPin(1111);
            atm.enterPin(1234);
            atm.withdraw();
        } finally {
            System.setOut(original);
        }

        List<String> lines = buffer.toString().lines().toList();
        assertThat(lines).containsExactly(
            "Insert card first",
            "Card inserted",
            "Wrong PIN",
            "PIN correct",
            "Cash withdrawn"
        );
    }
}

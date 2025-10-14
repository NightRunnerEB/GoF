package behavioral;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

final class CommandTest {

    @Test
    void commandManagerSupportsUndoAndRedo() {
        Document doc = new Document();
        CommandManager mgr = new CommandManager();

        mgr.execute(new AppendCommand(doc, "Hello"));
        mgr.execute(new AppendCommand(doc, ", world"));
        assertThat(doc.get()).isEqualTo("Hello, world");

        mgr.execute(new UppercaseCommand(doc));
        assertThat(doc.get()).isEqualTo("HELLO, WORLD");

        mgr.undo();
        assertThat(doc.get()).isEqualTo("Hello, world");

        mgr.undo();
        assertThat(doc.get()).isEqualTo("Hello");

        mgr.redo();
        assertThat(doc.get()).isEqualTo("Hello, world");

        mgr.redo();
        assertThat(doc.get()).isEqualTo("HELLO, WORLD");
    }
}

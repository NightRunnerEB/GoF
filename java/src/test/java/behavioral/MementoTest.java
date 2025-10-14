package behavioral;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

final class MementoTest {

    @Test
    void editorRestoresPreviousStatesUsingHistory() {
        TextEditor editor = new TextEditor();
        History history = new History();

        editor.setText("Hello");
        history.push(editor.save());

        editor.setText("Hello, world");
        history.push(editor.save());

        editor.setText("Overwritten");
        assertThat(editor.getText()).isEqualTo("Overwritten");

        editor.restore(history.pop());
        assertThat(editor.getText()).isEqualTo("Hello, world");

        editor.restore(history.pop());
        assertThat(editor.getText()).isEqualTo("Hello");
    }
}

package behavioral;

import java.util.ArrayDeque;
import java.util.Deque;

class TextEditor {

    private String text = "";

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public Snapshot save() {
        return new Snapshot(text);
    }

    public void restore(Snapshot snapshot) {
        this.text = snapshot.content;
    }

    static final class Snapshot {

        private final String content;

        Snapshot(String content) {
            this.content = content;
        }
    }
}

class History {

    private final Deque<TextEditor.Snapshot> snapshots = new ArrayDeque<>();

    public void push(TextEditor.Snapshot snapshot) {
        snapshots.push(snapshot);
    }

    public TextEditor.Snapshot pop() {
        return snapshots.pop();
    }

    public boolean isEmpty() {
        return snapshots.isEmpty();
    }
}

@SuppressWarnings("unused")
final class MementoExample {

    private MementoExample() {
    }

    public static void example() {
        TextEditor editor = new TextEditor();
        History history = new History();

        editor.setText("Hello");
        history.push(editor.save());

        editor.setText("Hello, world");
        history.push(editor.save());

        editor.setText("Overwritten");
        System.out.println("Current: " + editor.getText());

        editor.restore(history.pop());
        System.out.println("Undo #1: " + editor.getText());

        editor.restore(history.pop());
        System.out.println("Undo #2: " + editor.getText());
    }
}

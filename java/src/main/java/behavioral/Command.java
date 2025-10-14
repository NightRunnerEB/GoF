package behavioral;

import java.util.ArrayDeque;
import java.util.Deque;

// ===== Receiver =====
class Document {

    private final StringBuilder content = new StringBuilder();

    public void append(String s) {
        content.append(s);
    }

    public int length() {
        return content.length();
    }

    public void truncate(int newLen) {
        content.setLength(newLen);
    }

    public String get() {
        return content.toString();
    }
}

// ===== Command =====
interface Command {

    void execute();

    void undo();
}

// Append text
class AppendCommand implements Command {

    private final Document doc;
    private final String text;
    private Integer prevLen;

    public AppendCommand(Document doc, String text) {
        this.doc = doc;
        this.text = text;
    }

    @Override
    public void execute() {
        prevLen = doc.length();
        doc.append(text);
    }

    @Override
    public void undo() {
        if (prevLen != null) {
            doc.truncate(prevLen);
            prevLen = null;
        }
    }
}

// Uppercase whole doc
class UppercaseCommand implements Command {

    private final Document doc;
    private String backup;

    public UppercaseCommand(Document doc) {
        this.doc = doc;
    }

    @Override
    public void execute() {
        backup = doc.get();
        doc.truncate(0);
        doc.append(backup.toUpperCase());
    }

    @Override
    public void undo() {
        if (backup != null) {
            doc.truncate(0);
            doc.append(backup);
            backup = null;
        }
    }
}

// ===== Invoker =====
class CommandManager {

    private final Deque<Command> undo = new ArrayDeque<>();
    private final Deque<Command> redo = new ArrayDeque<>();

    public void execute(Command c) {
        c.execute();
        undo.push(c);
        redo.clear();
    }

    public boolean undo() {
        if (undo.isEmpty()) {
            return false;
        }
        Command c = undo.pop();
        c.undo();
        redo.push(c);
        return true;
    }

    public boolean redo() {
        if (redo.isEmpty()) {
            return false;
        }
        Command c = redo.pop();
        c.execute();
        undo.push(c);
        return true;
    }
}

// ===== Client =====
@SuppressWarnings("unused")
final class CommandExample {

    private CommandExample() {
    }

    public static void example() {
        Document doc = new Document();
        CommandManager manager = new CommandManager();

        manager.execute(new AppendCommand(doc, "Hello"));
        manager.execute(new AppendCommand(doc, ", world"));
        manager.execute(new UppercaseCommand(doc));
        System.out.println("Doc: " + doc.get());

        manager.undo();
        System.out.println("After undo: " + doc.get());

        manager.redo();
        System.out.println("After redo: " + doc.get());
    }
}

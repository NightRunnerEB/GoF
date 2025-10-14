package structural;

import java.util.*;

interface Component {

    String name();

    long size();                 // shared operation

    void print(int indent);      // for visualization
}

// Leaf node
final class Leaf implements Component {

    private final String name;
    private final long bytes;

    Leaf(String name, long bytes) {
        this.name = Objects.requireNonNull(name);
        this.bytes = bytes;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public long size() {
        return bytes;
    }

    @Override
    public void print(int indent) {
        System.out.printf("%s📄 %s (%d B)%n", " ".repeat(indent), name, bytes);
    }
}

// Composite node
final class Folder implements Component {

    private final String name;
    private final List<Component> children = new ArrayList<>();

    Folder(String name) {
        this.name = Objects.requireNonNull(name);
    }

    public void add(Component c) {
        children.add(Objects.requireNonNull(c));
    }

    public boolean remove(Component c) {
        return children.remove(c);
    }

    @Override
    public String name() {
        return name;
    }

    // key idea: delegate the shared operation to children
    @Override
    public long size() {
        long total = 0;
        for (Component c : children) {
            total += c.size();
        }
        return total;
    }

    @Override
    public void print(int indent) {
        System.out.printf("%s📁 %s (%d B)%n", " ".repeat(indent), name, size());
        for (Component c : children) {
            c.print(indent + 2);
        }
    }
}

@SuppressWarnings("unused")
final class CompositeExample {

    private CompositeExample() {
    }

    public static void example() {
        Folder root = new Folder("root");
        root.add(new Leaf("readme.md", 1200));
        root.add(new Leaf("logo.png", 48_000));

        Folder src = new Folder("src");
        src.add(new Leaf("Main.java", 3200));
        src.add(new Leaf("Utils.java", 1600));

        root.add(src);

        System.out.println("Total: " + root.size() + " B");
        root.print(0);

        root.remove(src);
        System.out.println("\nAfter remove:");
        root.print(0);
    }
}

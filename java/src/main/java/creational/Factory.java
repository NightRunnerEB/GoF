package creational;

// === Product ===
interface Button {

    void render();
}

final class WindowsButton implements Button {

    @Override
    public void render() {
        System.out.println("Render Windows button");
    }
}

final class LinuxButton implements Button {

    @Override
    public void render() {
        System.out.println("Render Linux button");
    }
}

// === Creator (factory method inside) ===
abstract class Dialog {

    // Factory method — subclasses decide which Button to create
    protected abstract Button createButton();

    // Shared logic stays the same
    public void renderWindow() {
        Button btn = createButton(); // client only uses abstraction
        btn.render();
    }
}

final class WindowsDialog extends Dialog {

    @Override
    protected Button createButton() {
        return new WindowsButton(); // concrete decision
    }
}

final class LinuxDialog extends Dialog {

    @Override
    protected Button createButton() {
        return new LinuxButton(); // alternative implementation
    }
}

@SuppressWarnings("unused")
final class FactoryExample {

    private FactoryExample() {
    }

    public static void example() {
        Dialog dialog = configureByOs(); // choose factory once
        dialog.renderWindow();           // client works with abstraction only
    }

    private static Dialog configureByOs() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            return new WindowsDialog();
        }
        if (os.contains("linux")) {
            return new LinuxDialog();
        }
        throw new IllegalStateException("Unsupported OS");
    }
}

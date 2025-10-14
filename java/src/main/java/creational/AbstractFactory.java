package creational;

// === Products ===
interface Button {

    void render();
}

interface Checkbox {

    void render();
}

class WindowsButton implements Button {

    @Override
    public void render() {
        System.out.println("Win button");
    }
}

class WindowsCheckbox implements Checkbox {

    @Override
    public void render() {
        System.out.println("Win checkbox");
    }
}

class LinuxButton implements Button {

    @Override
    public void render() {
        System.out.println("Linux button");
    }
}

class LinuxCheckbox implements Checkbox {

    @Override
    public void render() {
        System.out.println("Linux checkbox");
    }
}

// === Abstract factory ===
interface GUIFactory {

    Button createButton();

    Checkbox createCheckbox();
}

// === Concrete factories ===
class WindowsGUIFactory implements GUIFactory {

    @Override
    public Button createButton() {
        return new WindowsButton();
    }

    @Override
    public Checkbox createCheckbox() {
        return new WindowsCheckbox();
    }
}

class LinuxGUIFactory implements GUIFactory {

    @Override
    public Button createButton() {
        return new LinuxButton();
    }

    @Override
    public Checkbox createCheckbox() {
        return new LinuxCheckbox();
    }
}

@SuppressWarnings("unused")
final class AbstractFactoryExample {

    private AbstractFactoryExample() {
    }

    public static void example() {
        GUIFactory factory = detectFactory(); // could come from CONFIG/ENV
        Button btn = factory.createButton();
        Checkbox chk = factory.createCheckbox();
        btn.render();
        chk.render();
    }

    private static GUIFactory detectFactory() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            return new WindowsGUIFactory();
        } else {
            return new LinuxGUIFactory();
        }
    }
}

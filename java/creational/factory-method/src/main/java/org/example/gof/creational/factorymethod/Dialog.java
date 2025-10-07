package org.example.gof.creational.factorymethod;

public abstract class Dialog {
    public String render() {
        Button button = createButton();
        return "Rendering dialog with " + button.render();
    }

    protected abstract Button createButton();
}

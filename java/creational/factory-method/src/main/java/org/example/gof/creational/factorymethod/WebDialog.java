package org.example.gof.creational.factorymethod;

public class WebDialog extends Dialog {
    @Override
    protected Button createButton() {
        return new HtmlButton();
    }
}

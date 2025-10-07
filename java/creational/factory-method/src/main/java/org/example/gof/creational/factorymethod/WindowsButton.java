package org.example.gof.creational.factorymethod;

public class WindowsButton implements Button {
    @Override
    public String render() {
        return "Windows button";
    }
}

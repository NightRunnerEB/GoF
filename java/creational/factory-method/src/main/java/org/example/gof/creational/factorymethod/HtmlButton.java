package org.example.gof.creational.factorymethod;

public class HtmlButton implements Button {
    @Override
    public String render() {
        return "HTML button";
    }
}

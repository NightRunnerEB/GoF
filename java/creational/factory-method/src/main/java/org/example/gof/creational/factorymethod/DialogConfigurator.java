package org.example.gof.creational.factorymethod;

public final class DialogConfigurator {
    private DialogConfigurator() {
    }

    public static Dialog forEnvironment(String environment) {
        if ("desktop".equalsIgnoreCase(environment)) {
            return new WindowsDialog();
        }
        return new WebDialog();
    }
}

package org.example.gof.creational.factorymethod;

public final class DialogApp {
    private DialogApp() {
    }

    public static void main(String[] args) {
        String environment = args.length > 0 ? args[0] : "web";
        Dialog dialog = DialogConfigurator.forEnvironment(environment);
        System.out.println(dialog.render());
    }
}

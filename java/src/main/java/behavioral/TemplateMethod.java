package behavioral;

abstract class Beverage {

    // Template method
    public final void prepareRecipe() {
        boilWater();
        brew();
        pourInCup();
        addCondiments();
    }

    private void boilWater() {
        System.out.println("Boiling water");
    }

    private void pourInCup() {
        System.out.println("Pouring into cup");
    }

    // Abstract steps differ in subclasses
    protected abstract void brew();

    protected abstract void addCondiments();
}

class Tea extends Beverage {

    @Override
    protected void brew() {
        System.out.println("Steeping the tea");
    }

    @Override
    protected void addCondiments() {
        System.out.println("Adding lemon");
    }
}

class Coffee extends Beverage {

    @Override
    protected void brew() {
        System.out.println("Dripping coffee through filter");
    }

    @Override
    protected void addCondiments() {
        System.out.println("Adding sugar and milk");
    }
}

@SuppressWarnings("unused")
final class TemplateMethodExample {

    private TemplateMethodExample() {
    }

    public static void example() {
        new Tea().prepareRecipe();
        new Coffee().prepareRecipe();
    }
}

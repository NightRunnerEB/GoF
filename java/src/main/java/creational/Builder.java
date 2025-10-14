package creational;

class Meal {

    private final String mainDish;      // required
    private final String side;          // optional
    private final String drink;         // optional
    private final String dessert;       // optional

    // private constructor used only by the builder
    private Meal(Builder b) {
        this.mainDish = b.mainDish;
        this.side = b.side;
        this.drink = b.drink;
        this.dessert = b.dessert;
    }

    // === Builder ===
    public static class Builder {

        private final String mainDish; // required
        private String side;
        private String drink;
        private String dessert;

        public Builder(String mainDish) {
            this.mainDish = mainDish;
        }

        public Builder addSide(String side) {
            this.side = side;
            return this;
        }

        public Builder addDrink(String drink) {
            this.drink = drink;
            return this;
        }

        public Builder addDessert(String dessert) {
            this.dessert = dessert;
            return this;
        }

        public Meal build() {
            return new Meal(this);
        }
    }

    @Override
    public String toString() {
        return "Meal{"
                + "main='" + mainDish + '\''
                + ", side='" + side + '\''
                + ", drink='" + drink + '\''
                + ", dessert='" + dessert + '\''
                + '}';
    }
}

@SuppressWarnings("unused")
final class BuilderExample {

    private BuilderExample() {
    }

    public static void example() {
        Meal meal = new Meal.Builder("Steak")
                .addSide("Salad")
                .addDrink("Tea")
                .build();

        System.out.println(meal);
    }
}

struct Meal {
    main_dish: String, // required
    side: Option<String>,
    drink: Option<String>,
    dessert: Option<String>,
}

impl Meal {
    fn new(main_dish: String) -> Self {
        Meal {
            main_dish,
            side: None,
            drink: None,
            dessert: None,
        }
    }
}

// === Builder ===
struct MealBuilder {
    meal: Meal,
}

impl MealBuilder {
    fn new(main: &str) -> Self {
        MealBuilder {
            meal: Meal::new(main.to_string()),
        }
    }
    fn add_side(mut self, side: &str) -> Self {
        self.meal.side = Some(side.to_string());
        self
    }
    fn add_drink(mut self, drink: &str) -> Self {
        self.meal.drink = Some(drink.to_string());
        self
    }
    fn add_dessert(mut self, dessert: &str) -> Self {
        self.meal.dessert = Some(dessert.to_string());
        self
    }
    fn build(self) -> Meal {
        self.meal // you could add validation here
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn example() {
        let meal = MealBuilder::new("Steak")
            .add_side("Salad")
            .add_drink("Iced Tea")
            .build();

        println!("Main: {}", meal.main_dish);
    }
}

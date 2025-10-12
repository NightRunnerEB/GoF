use std::collections::HashMap;
use std::sync::Arc;

// ---- Flyweight: общее состояние ----
struct TreeType {
    name: String,    // intrinsic
    texture: String, // intrinsic
}

impl TreeType {
    fn new(name: &str, texture: &str) -> Self {
        Self {
            name: name.into(),
            texture: texture.into(),
        }
    }
    fn draw(&self, x: i32, y: i32) {
        println!(
            "Drawing {} with {} at ({},{})",
            self.name, self.texture, x, y
        );
    }
}

// ---- Фабрика-пул flyweight'ов ----
struct TreeFactory {
    pool: HashMap<String, Arc<TreeType>>,
}

impl TreeFactory {
    fn new() -> Self {
        Self {
            pool: HashMap::new(),
        }
    }
    fn get_tree_type(&mut self, name: &str, texture: &str) -> Arc<TreeType> {
        let key = format!("{}:{}", name, texture);
        self.pool
            .entry(key)
            .or_insert_with(|| Arc::new(TreeType::new(name, texture)))
            .clone() // отдаём ещё одну "умную ссылку" на общий объект
    }
    fn pool_size(&self) -> usize {
        self.pool.len()
    }
}

// ---- Внешнее состояние узла (каждое дерево) ----
struct Tree {
    x: i32,              // extrinsic
    y: i32,              // extrinsic
    kind: Arc<TreeType>, // shared intrinsic
}

impl Tree {
    fn new(x: i32, y: i32, kind: Arc<TreeType>) -> Self {
        Self { x, y, kind }
    }
    fn draw(&self) {
        self.kind.draw(self.x, self.y);
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn example() {
        let mut factory = TreeFactory::new();

        // Берём/shared типы (intrinsic). Arc делает шаринг безопасным и дешёвым.
        let oak = factory.get_tree_type("Oak", "oak.png");
        let pine = factory.get_tree_type("Pine", "pine.png");

        // Создаём много "логических" деревьев с разными координатами,
        // но ссылающихся на несколько общих TreeType.
        let forest = vec![
            Tree::new(1, 2, oak.clone()),
            Tree::new(3, 4, oak.clone()),
            Tree::new(5, 6, pine.clone()),
            Tree::new(7, 8, pine.clone()),
        ];

        for t in &forest {
            t.draw();
        }

        println!(
            "Unique flyweights (TreeType) in pool: {}",
            factory.pool_size()
        );
    }
}

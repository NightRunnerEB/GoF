use std::collections::HashMap;
use std::sync::Arc;

// ---- Flyweight: shared intrinsic state ----
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

// ---- Flyweight factory / cache ----
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
            .clone() // hand out another shared pointer to the pooled object
    }
    fn pool_size(&self) -> usize {
        self.pool.len()
    }
}

// ---- Extrinsic per-tree state ----
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

        // Acquire shared intrinsic types. Arc keeps sharing safe and cheap.
        let oak = factory.get_tree_type("Oak", "oak.png");
        let pine = factory.get_tree_type("Pine", "pine.png");

        // Create many logical trees with different coordinates that share only a few TreeTypes.
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

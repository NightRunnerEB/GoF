#[derive(Clone)]
struct GameState {
    level: u32,
    health: u32,
}

// Originator
struct Game {
    state: GameState,
}
impl Game {
    fn new() -> Self {
        Self {
            state: GameState {
                level: 1,
                health: 100,
            },
        }
    }

    fn play(&mut self) {
        self.state.level += 1;
        self.state.health -= 10;
        println!(
            "Playing... level={}, health={}",
            self.state.level, self.state.health
        );
    }

    fn save(&self) -> GameState {
        self.state.clone() // Memento
    }

    fn load(&mut self, m: GameState) {
        self.state = m;
        println!(
            "Loaded! level={}, health={}",
            self.state.level, self.state.health
        );
    }
}

// Caretaker
struct History {
    stack: Vec<GameState>,
}
impl History {
    fn new() -> Self {
        Self { stack: vec![] }
    }
    fn push(&mut self, m: GameState) {
        self.stack.push(m);
    }
    fn pop(&mut self) -> Option<GameState> {
        self.stack.pop()
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn example() {
        let mut game = Game::new();
        let mut history = History::new();

        history.push(game.save());

        game.play();
        history.push(game.save());

        game.play();
        println!("Game progressed...");

        // Undo
        if let Some(m) = history.pop() {
            game.load(m);
        }
        if let Some(m) = history.pop() {
            game.load(m);
        }
    }
}

trait State {
    fn handle_input(&mut self, input: &str) -> Option<Box<dyn State>>;
}

struct Standing;
impl State for Standing {
    fn handle_input(&mut self, input: &str) -> Option<Box<dyn State>> {
        match input {
            "walk" => {
                println!("Player starts walking");
                Some(Box::new(Walking))
            }
            "jump" => {
                println!("Player jumps");
                Some(Box::new(Jumping))
            }
            _ => {
                println!("Player stands still");
                None
            }
        }
    }
}

struct Walking;
impl State for Walking {
    fn handle_input(&mut self, input: &str) -> Option<Box<dyn State>> {
        match input {
            "stop" => {
                println!("Player stops");
                Some(Box::new(Standing))
            }
            "jump" => {
                println!("Player jumps while walking");
                Some(Box::new(Jumping))
            }
            _ => {
                println!("Player keeps walking");
                None
            }
        }
    }
}

struct Jumping;
impl State for Jumping {
    fn handle_input(&mut self, _input: &str) -> Option<Box<dyn State>> {
        println!("Player lands");
        Some(Box::new(Standing))
    }
}

struct Player {
    state: Box<dyn State>,
}
impl Player {
    fn new() -> Self {
        Self {
            state: Box::new(Standing),
        }
    }

    fn handle_input(&mut self, input: &str) {
        if let Some(new_state) = self.state.handle_input(input) {
            self.state = new_state;
        }
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn example() {
        let mut player = Player::new();
        player.handle_input("walk");
        player.handle_input("jump");
        player.handle_input("stop");
    }
}

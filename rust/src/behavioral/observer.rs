trait Observer {
    fn update(&self, hp: i32);
}

struct Player {
    hp: i32,
    observers: Vec<Box<dyn Observer>>,
}

impl Player {
    fn new() -> Self {
        Self {
            hp: 100,
            observers: vec![],
        }
    }

    fn attach(&mut self, obs: Box<dyn Observer>) {
        self.observers.push(obs);
    }

    fn set_hp(&mut self, hp: i32) {
        self.hp = hp;
        for o in &self.observers {
            o.update(self.hp);
        }
    }
}

// Observers
struct Hud;
impl Observer for Hud {
    fn update(&self, hp: i32) {
        println!("HUD: Player HP = {}", hp);
    }
}

struct Logger;
impl Observer for Logger {
    fn update(&self, hp: i32) {
        println!("Logger: HP changed to {}", hp);
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn example() {
        let mut player = Player::new();
        player.attach(Box::new(Hud));
        player.attach(Box::new(Logger));

        player.set_hp(80);
        player.set_hp(50);
    }
}

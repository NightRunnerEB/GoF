pub trait Visitor {
    fn visit_monster(&mut self, monster: &Monster);
    fn visit_player(&mut self, player: &Player);
}

pub trait Entity {
    fn accept(&self, visitor: &mut dyn Visitor);
}

pub struct Monster {
    pub hp: i32,
}

impl Entity for Monster {
    fn accept(&self, visitor: &mut dyn Visitor) {
        visitor.visit_monster(self);
    }
}

pub struct Player {
    pub name: String,
}

impl Entity for Player {
    fn accept(&self, visitor: &mut dyn Visitor) {
        visitor.visit_player(self);
    }
}

pub struct RenderVisitor;

impl Visitor for RenderVisitor {
    fn visit_monster(&mut self, monster: &Monster) {
        println!("Draw monster with HP {}", monster.hp);
    }

    fn visit_player(&mut self, player: &Player) {
        println!("Draw player {}", player.name);
    }
}

pub struct SaveVisitor;

impl Visitor for SaveVisitor {
    fn visit_monster(&mut self, monster: &Monster) {
        println!("Save monster {{hp:{}}}", monster.hp);
    }

    fn visit_player(&mut self, player: &Player) {
        println!("Save player {{name:{}}}", player.name);
    }
}

pub fn demo() {
    let entities: Vec<Box<dyn Entity>> = vec![
        Box::new(Monster { hp: 100 }),
        Box::new(Player {
            name: "Alice".to_string(),
        }),
    ];

    let mut renderer = RenderVisitor;
    let mut saver = SaveVisitor;

    for entity in &entities {
        entity.accept(&mut renderer);
    }

    for entity in &entities {
        entity.accept(&mut saver);
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn visitors_process_entities() {
        demo();
    }
}

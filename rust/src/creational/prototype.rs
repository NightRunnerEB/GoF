/// In Rust, `Clone` is the built-in prototype mechanism, so we can use it literally.

#[derive(Clone, Debug)]
struct Character {
    class: String,
    hp: u32,
    mana: u32,
    weapon: String,
}

fn main() {
    let mage_proto = Character {
        class: "Mage".into(),
        hp: 100,
        mana: 200,
        weapon: "Staff".into(),
    };

    // Clone and tweak → quick creation
    let mut new_mage = mage_proto.clone();
    new_mage.weapon = "Epic Staff".into();

    println!("Base: {:?}", mage_proto);
    println!("New:  {:?}", new_mage);
}

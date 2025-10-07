use factory_method::configure_dialog;

fn main() {
    let environment = std::env::args().nth(1).unwrap_or_else(|| "web".to_string());
    let dialog = configure_dialog(&environment);
    println!("{}", dialog.render());
}

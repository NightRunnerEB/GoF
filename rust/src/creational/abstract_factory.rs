trait Button {
    fn render(&self);
}
trait Checkbox {
    fn render(&self);
}

struct WinButton;
impl Button for WinButton {
    fn render(&self) {
        println!("Win button");
    }
}
struct WinCheckbox;
impl Checkbox for WinCheckbox {
    fn render(&self) {
        println!("Win checkbox");
    }
}

struct LinuxButton;
impl Button for LinuxButton {
    fn render(&self) {
        println!("Linux button");
    }
}
struct LinuxCheckbox;
impl Checkbox for LinuxCheckbox {
    fn render(&self) {
        println!("Linux checkbox");
    }
}

// === Abstract factory ===
trait GUIFactory {
    fn create_button(&self) -> Box<dyn Button>;
    fn create_checkbox(&self) -> Box<dyn Checkbox>;
}

// === Concrete factories ===
struct WindowsFactory;
impl GUIFactory for WindowsFactory {
    fn create_button(&self) -> Box<dyn Button> {
        Box::new(WinButton)
    }
    fn create_checkbox(&self) -> Box<dyn Checkbox> {
        Box::new(WinCheckbox)
    }
}

struct LinuxFactory;
impl GUIFactory for LinuxFactory {
    fn create_button(&self) -> Box<dyn Button> {
        Box::new(LinuxButton)
    }
    fn create_checkbox(&self) -> Box<dyn Checkbox> {
        Box::new(LinuxCheckbox)
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn example() {
        let os = "linux"; // should come from config
        let factory: Box<dyn GUIFactory> = match os {
            "windows" => Box::new(WindowsFactory),
            _ => Box::new(LinuxFactory),
        };

        let btn = factory.create_button();
        let chk = factory.create_checkbox();
        btn.render();
        chk.render();
    }
}

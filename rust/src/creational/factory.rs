trait Button {
    fn render(&self);
}

struct WindowsButton;
impl Button for WindowsButton {
    fn render(&self) {
        println!("Render Windows button");
    }
}

struct LinuxButton;
impl Button for LinuxButton {
    fn render(&self) {
        println!("Render Linux button");
    }
}

// Factory Method abstraction
trait Dialog {
    fn create_button(&self) -> Box<dyn Button>; // <-- factory method
    fn render(&self) {
        let button = self.create_button();
        button.render();
    }
}

struct WindowsDialog;
impl Dialog for WindowsDialog {
    fn create_button(&self) -> Box<dyn Button> {
        Box::new(WindowsButton)
    }
}

struct LinuxDialog;
impl Dialog for LinuxDialog {
    fn create_button(&self) -> Box<dyn Button> {
        Box::new(LinuxButton)
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn example() {
        // Imagine the platform choice comes from config, CLI, feature flag, etc.
        let os = "linux"; // could be "windows" or read from std::env::var

        // Client only knows about the Dialog abstraction, not the concrete buttons
        let dialog: Box<dyn Dialog> = match os {
            "windows" => Box::new(WindowsDialog),
            "linux" => Box::new(LinuxDialog),
            _ => panic!("Unsupported OS"),
        };

        // Client calls the abstraction
        dialog.render();
    }
}

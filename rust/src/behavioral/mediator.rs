trait Mediator {
    fn notify(&mut self, sender: &str, event: &str);
}

struct Button {
    mediator: Option<Box<dyn Mediator>>,
}
impl Button {
    fn new() -> Self {
        Self { mediator: None }
    }
    fn click(&mut self) {
        println!("Button clicked");
        if let Some(m) = self.mediator.as_mut() {
            m.notify("Button", "click");
        }
    }
}

struct Checkbox {
    checked: bool,
    mediator: Option<Box<dyn Mediator>>,
}
impl Checkbox {
    fn new() -> Self {
        Self {
            checked: false,
            mediator: None,
        }
    }
    fn toggle(&mut self) {
        self.checked = !self.checked;
        println!("Checkbox is now {}", self.checked);
        if let Some(m) = self.mediator.as_mut() {
            m.notify("Checkbox", "toggle");
        }
    }
}

// Конкретный медиатор: связывает кнопку и чекбокс
struct Dialog {
    button: Button,
    checkbox: Checkbox,
}
impl Dialog {
    fn new() -> Self {
        Self {
            button: Button::new(),
            checkbox: Checkbox::new(),
        }
    }
}
impl Mediator for Dialog {
    fn notify(&mut self, sender: &str, event: &str) {
        match (sender, event) {
            ("Button", "click") => {
                println!("Mediator: button clicked -> toggling checkbox");
                self.checkbox.toggle();
            }
            ("Checkbox", "toggle") => {
                println!("Mediator: checkbox toggled -> could update button");
            }
            _ => {}
        }
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn example() {
        let mut dialog = Dialog::new();
        dialog.notify("Button", "click"); // simulate click
    }
}

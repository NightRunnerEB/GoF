trait Command {
    fn execute(&self);
    fn undo(&self);
}

// ===== Receiver: Light =====
struct Light;
impl Light {
    fn on(&self) {
        println!("Light is ON");
    }
    fn off(&self) {
        println!("Light is OFF");
    }
}

// ===== Receiver: TV =====
struct TV;
impl TV {
    fn on(&self) {
        println!("TV is ON");
    }
    fn off(&self) {
        println!("TV is OFF");
    }
}

// ===== Concrete Commands =====
struct LightOnCommand<'a> {
    light: &'a Light,
}
impl<'a> Command for LightOnCommand<'a> {
    fn execute(&self) {
        self.light.on();
    }
    fn undo(&self) {
        self.light.off();
    }
}

struct LightOffCommand<'a> {
    light: &'a Light,
}
impl<'a> Command for LightOffCommand<'a> {
    fn execute(&self) {
        self.light.off();
    }
    fn undo(&self) {
        self.light.on();
    }
}

struct TVOnCommand<'a> {
    tv: &'a TV,
}
impl<'a> Command for TVOnCommand<'a> {
    fn execute(&self) {
        self.tv.on();
    }
    fn undo(&self) {
        self.tv.off();
    }
}

struct TVOffCommand<'a> {
    tv: &'a TV,
}
impl<'a> Command for TVOffCommand<'a> {
    fn execute(&self) {
        self.tv.off();
    }
    fn undo(&self) {
        self.tv.on();
    }
}

// ===== Invoker: RemoteControl =====
struct RemoteControl<'a> {
    command: Option<Box<dyn Command + 'a>>,
}
impl<'a> RemoteControl<'a> {
    fn new() -> Self {
        Self { command: None }
    }
    fn set_command(&mut self, cmd: Box<dyn Command + 'a>) {
        self.command = Some(cmd);
    }
    fn press_button(&self) {
        if let Some(c) = &self.command {
            c.execute();
        }
    }
    fn press_undo(&self) {
        if let Some(c) = &self.command {
            c.undo();
        }
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn example() {
        let light = Light;
        let tv = TV;

        let mut remote = RemoteControl::new();

        // Controlling the light
        remote.set_command(Box::new(LightOnCommand { light: &light }));
        remote.press_button(); // Light is ON
        remote.press_undo(); // Light is OFF

        // Controlling the TV
        remote.set_command(Box::new(TVOnCommand { tv: &tv }));
        remote.press_button(); // TV is ON
        remote.set_command(Box::new(TVOffCommand { tv: &tv }));
        remote.press_button(); // TV is OFF
    }
}

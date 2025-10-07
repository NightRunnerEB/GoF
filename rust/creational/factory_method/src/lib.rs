//! Пример паттерна "Фабричный метод" на Rust.

/// Абстрактный продукт.
pub trait DialogButton {
    fn render(&self) -> &'static str;
}

/// Конкретная реализация кнопки для Windows.
pub struct WindowsButton;

impl DialogButton for WindowsButton {
    fn render(&self) -> &'static str {
        "Windows button"
    }
}

/// Конкретная реализация кнопки для Web.
pub struct HtmlButton;

impl DialogButton for HtmlButton {
    fn render(&self) -> &'static str {
        "HTML button"
    }
}

/// Абстрактный создатель, в котором инкапсулирован фабричный метод.
pub trait Dialog {
    fn create_button(&self) -> Box<dyn DialogButton>;

    fn render(&self) -> String {
        let button = self.create_button();
        format!("Rendering dialog with {}", button.render())
    }
}

/// Конкретный диалог, возвращающий Windows-кнопку.
pub struct WindowsDialog;

impl Dialog for WindowsDialog {
    fn create_button(&self) -> Box<dyn DialogButton> {
        Box::new(WindowsButton)
    }
}

/// Конкретный диалог, возвращающий HTML-кнопку.
pub struct WebDialog;

impl Dialog for WebDialog {
    fn create_button(&self) -> Box<dyn DialogButton> {
        Box::new(HtmlButton)
    }
}

/// Контекст, выбирающий нужный диалог в рантайме.
pub fn configure_dialog(environment: &str) -> Box<dyn Dialog> {
    match environment {
        "desktop" => Box::new(WindowsDialog),
        _ => Box::new(WebDialog),
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn windows_dialog_renders_windows_button() {
        let dialog = WindowsDialog;
        assert!(dialog.render().contains("Windows button"));
    }

    #[test]
    fn web_dialog_renders_html_button() {
        let dialog = WebDialog;
        assert!(dialog.render().contains("HTML button"));
    }

    #[test]
    fn configure_dialog_picks_correct_variant() {
        let desktop_dialog = configure_dialog("desktop");
        assert!(desktop_dialog.render().contains("Windows button"));

        let web_dialog = configure_dialog("web");
        assert!(web_dialog.render().contains("HTML button"));
    }
}

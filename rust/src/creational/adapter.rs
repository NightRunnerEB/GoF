struct OldLogger;
impl OldLogger {
    fn log(&self, msg: &str) {
        println!("[OLD] {}", msg);
    }
}

// Новый интерфейс, требуемый системой:
trait Logger {
    fn write(&self, message: &str);
}

// Адаптер, совмещающий OldLogger с новым интерфейсом:
struct LoggerAdapter {
    old_logger: OldLogger,
}
impl Logger for LoggerAdapter {
    fn write(&self, message: &str) {
        // Перенаправляем вызов в старый метод:
        self.old_logger.log(message);
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn example() {
        // Использование адаптера:
        let adapter = LoggerAdapter {
            old_logger: OldLogger,
        };
        adapter.write("Some important information");
    }
}

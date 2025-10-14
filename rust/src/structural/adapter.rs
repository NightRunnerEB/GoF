struct OldLogger;
impl OldLogger {
    fn log(&self, msg: &str) {
        println!("[OLD] {}", msg);
    }
}

// New interface required by the system:
trait Logger {
    fn write(&self, message: &str);
}

// Adapter that wraps OldLogger with the new interface:
struct LoggerAdapter {
    old_logger: OldLogger,
}
impl Logger for LoggerAdapter {
    fn write(&self, message: &str) {
        // Forward the call to the legacy method:
        self.old_logger.log(message);
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn example() {
        // Using the adapter:
        let adapter = LoggerAdapter {
            old_logger: OldLogger,
        };
        adapter.write("Some important information");
    }
}

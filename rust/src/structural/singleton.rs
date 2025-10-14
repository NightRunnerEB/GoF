use std::collections::HashMap;
use std::sync::Once;

/// Rust does not have built-in lazy singletons, but we can emulate them with
/// a `static` pointer plus `OnceLock`, or via helpers such as `lazy_static`/`Lazy`.
/// Note: singletons are less common in Rust than in classical OOP languages
/// because global mutable state is discouraged; configuration structs passed by
/// reference or dependency injection is often preferred. Still, if you need a
/// global instance (e.g., a logger), this illustrates one approach.
struct Config {
    settings: HashMap<String, String>,
}

// Global storage for the singleton instance.
static mut CONFIG: *const Config = std::ptr::null();
static INIT: Once = Once::new();

fn get_config() -> &'static Config {
    unsafe {
        // Initialize exactly once.
        INIT.call_once(|| {
            let mut settings = HashMap::new();
            settings.insert("version".into(), "1.0".into());
            settings.insert("app_name".into(), "MyApp".into());
            let config = Config { settings };
            CONFIG = Box::into_raw(Box::new(config)); // leaked intentionally for program lifetime
        });
        &*CONFIG // return reference to the singleton
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn example() {
        let conf = get_config();
        println!("App name: {}", conf.settings.get("app_name").unwrap());
    }
}

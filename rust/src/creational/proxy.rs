trait Data {
    fn load(&mut self) -> &str;
}

// Реальный объект (тяжёлый)
struct RealData {
    content: String,
}

impl RealData {
    fn new() -> Self {
        println!("Loading real data...");
        Self {
            content: "Big data from DB".to_string(),
        }
    }
}

impl Data for RealData {
    fn load(&mut self) -> &str {
        &self.content
    }
}

// Прокси
struct ProxyData {
    real: Option<RealData>,
}

impl ProxyData {
    fn new() -> Self {
        Self { real: None }
    }
}

impl Data for ProxyData {
    fn load(&mut self) -> &str {
        if self.real.is_none() {
            self.real = Some(RealData::new()); // lazy creation
        }
        self.real.as_ref().unwrap().load()
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn example() {
        let mut data: Box<dyn Data> = Box::new(ProxyData::new());

        println!("Program started...");
        // пока RealData не создан

        println!("First call: {}", data.load()); // создаётся RealData
        println!("Second call: {}", data.load()); // используется готовый
    }
}

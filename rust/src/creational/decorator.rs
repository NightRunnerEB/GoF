trait DataSource {
    fn write(&mut self, data: &str);
    fn read(&self) -> String;
}

struct FileDataSource {
    storage: String,
}
impl FileDataSource {
    fn new() -> Self {
        Self {
            storage: String::new(),
        }
    }
}
impl DataSource for FileDataSource {
    fn write(&mut self, data: &str) {
        self.storage = data.to_string();
    }
    fn read(&self) -> String {
        self.storage.clone()
    }
}

struct EncryptionDecorator {
    inner: Box<dyn DataSource>,
}
impl EncryptionDecorator {
    fn new(inner: Box<dyn DataSource>) -> Self {
        Self { inner }
    }
}
impl DataSource for EncryptionDecorator {
    fn write(&mut self, data: &str) {
        self.inner.write(&format!("[enc]{}", data));
    }
    fn read(&self) -> String {
        self.inner.read().replace("[enc]", "")
    }
}

struct CompressionDecorator {
    inner: Box<dyn DataSource>,
}
impl CompressionDecorator {
    fn new(inner: Box<dyn DataSource>) -> Self {
        Self { inner }
    }
}
impl DataSource for CompressionDecorator {
    fn write(&mut self, data: &str) {
        self.inner.write(&format!("[zip]{}", data));
    }
    fn read(&self) -> String {
        self.inner.read().replace("[zip]", "")
    }
}

// Builder-стиль
struct DataSourceBuilder {
    current: Box<dyn DataSource>,
}
impl DataSourceBuilder {
    fn new(base: Box<dyn DataSource>) -> Self {
        Self { current: base }
    }

    fn add_encryption(mut self) -> Self {
        self.current = Box::new(EncryptionDecorator::new(self.current));
        self
    }

    fn add_compression(mut self) -> Self {
        self.current = Box::new(CompressionDecorator::new(self.current));
        self
    }

    fn build(self) -> Box<dyn DataSource> {
        self.current
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn example() {
        let mut ds = DataSourceBuilder::new(Box::new(FileDataSource::new()))
            .add_compression()
            .add_encryption()
            .build();

        ds.write("Hello");
        println!("{}", ds.read());
    }
}

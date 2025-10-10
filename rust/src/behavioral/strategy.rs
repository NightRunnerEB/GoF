trait CompressionStrategy {
    fn compress(&self, file: &str);
}

struct ZipCompression;
impl CompressionStrategy for ZipCompression {
    fn compress(&self, file: &str) {
        println!("Compressing {} as ZIP", file);
    }
}

struct GzipCompression;
impl CompressionStrategy for GzipCompression {
    fn compress(&self, file: &str) {
        println!("Compressing {} as GZIP", file);
    }
}

struct Compressor {
    strategy: Box<dyn CompressionStrategy>,
}

impl Compressor {
    fn new(strategy: Box<dyn CompressionStrategy>) -> Self {
        Self { strategy }
    }
    fn set_strategy(&mut self, strategy: Box<dyn CompressionStrategy>) {
        self.strategy = strategy;
    }
    fn compress(&self, file: &str) {
        self.strategy.compress(file);
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn example() {
        let mut compressor = Compressor::new(Box::new(ZipCompression));
        compressor.compress("file.txt");

        compressor.set_strategy(Box::new(GzipCompression));
        compressor.compress("file.txt");
    }
}

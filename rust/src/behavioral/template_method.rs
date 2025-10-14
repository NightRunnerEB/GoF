trait Report {
    fn collect_data(&self);
    fn format(&self);
    fn export(&self);

    // Template method
    fn generate(&self) {
        println!("Start report generation...");
        self.collect_data();
        self.format();
        self.export();
        println!("Report done.");
    }
}

struct PdfReport;
impl Report for PdfReport {
    fn collect_data(&self) {
        println!("Collecting data for PDF...");
    }
    fn format(&self) {
        println!("Formatting PDF layout...");
    }
    fn export(&self) {
        println!("Exporting as PDF file");
    }
}

struct HtmlReport;
impl Report for HtmlReport {
    fn collect_data(&self) {
        println!("Collecting data for HTML...");
    }
    fn format(&self) {
        println!("Building HTML structure...");
    }
    fn export(&self) {
        println!("Exporting as HTML file");
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn example() {
        let pdf = PdfReport;
        let html = HtmlReport;

        pdf.generate();
        html.generate();
    }
}

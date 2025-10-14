trait Node {
    fn name(&self) -> &str;
    fn size(&self) -> u64; // unified interface
    fn print(&self, indent: usize); // helper for visualization
}

// ---------- Leaf ----------
struct File {
    name: String,
    bytes: u64,
}

impl File {
    fn new(name: impl Into<String>, bytes: u64) -> Self {
        Self {
            name: name.into(),
            bytes,
        }
    }
}

impl Node for File {
    fn name(&self) -> &str {
        &self.name
    }
    fn size(&self) -> u64 {
        self.bytes
    }
    fn print(&self, indent: usize) {
        println!(
            "{:indent$}📄 {} ({} B)",
            "",
            self.name,
            self.bytes,
            indent = indent
        );
    }
}

// ---------- Composite ----------
struct Folder {
    name: String,
    children: Vec<Box<dyn Node>>,
}

impl Folder {
    fn new(name: impl Into<String>) -> Self {
        Self {
            name: name.into(),
            children: Vec::new(),
        }
    }

    fn add(&mut self, child: Box<dyn Node>) {
        self.children.push(child);
    }

    fn remove_by_name(&mut self, target: &str) -> bool {
        if let Some(pos) = self.children.iter().position(|c| c.name() == target) {
            self.children.remove(pos);
            true
        } else {
            false
        }
    }
}

impl Node for Folder {
    fn name(&self) -> &str {
        &self.name
    }

    // Key idea: the composite delegates the operation to all children
    fn size(&self) -> u64 {
        self.children.iter().map(|c| c.size()).sum()
    }

    fn print(&self, indent: usize) {
        println!(
            "{:indent$}📁 {} ({} B)",
            "",
            self.name,
            self.size(),
            indent = indent
        );
        for child in &self.children {
            child.print(indent + 2);
        }
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn example() {
        // Build the hierarchy:
        let mut root = Folder::new("root");
        root.add(Box::new(File::new("readme.md", 1200)));
        root.add(Box::new(File::new("logo.png", 48_000)));

        let mut src = Folder::new("src");
        src.add(Box::new(File::new("main.rs", 3_200)));
        src.add(Box::new(File::new("lib.rs", 1_600)));

        root.add(Box::new(src));

        // Client talks to Node without caring whether it's a leaf or composite:
        println!("Total size: {} B", root.size());
        root.print(0);

        // Removal example:
        // (only Folder supports it — which is fine; unification applies to shared operations)
        let removed = root.remove_by_name("logo.png");
        println!("Removed logo.png? {}", removed);
        println!("Total after remove: {} B", root.size());
    }
}

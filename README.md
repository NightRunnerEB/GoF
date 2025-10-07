# Gang of Four Patterns in Rust and Java

This repository hosts educational scaffolding for implementing classic Gang of Four design patterns in Rust and Java. Each language has its own workspace so you can evolve patterns independently while keeping the directory structure aligned.

## Structure

```
.
├── java/           # Gradle multi-project workspace
├── rust/           # Cargo workspace
├── docs/           # Learning notes and diagrams
└── README.md
```

Both languages follow the same high-level taxonomy: `creational`, `structural`, and `behavioral`. Every pattern lives in its own module or crate with a single source file where you keep both the implementation and the client usage example.

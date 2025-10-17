# Gang of Four Patterns in Rust and Java

This repository hosts scaffolding for learning the classic Gang of Four design patterns in Rust and Java. Each language lives in its own workspace so you can iterate independently while keeping the folder layout aligned.

## Structure

```
.
├── docs/           # Learning notes and diagrams
├── java/           # Gradle multi-project workspace
├── rust/           # Cargo workspace
└── README.md
```

Both ecosystems follow the same taxonomy (`creational`, `structural`, `behavioral`). Every pattern sits in a dedicated module/crate with a single source file that contains both the implementation and the basic client usage.

## Navigation

- `docs/` contains explanation materials.
- `java/` and `rust/` contain language-specific workspaces with matching pattern lists.

## License

Distributed under the MIT License. See `LICENSE` for details.

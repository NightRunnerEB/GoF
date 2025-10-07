# Rust Workspace

Rust patterns are organized as a Cargo workspace. Each pattern has its own crate inside a category directory (`creational`, `structural`, `behavioral`). Keep both the implementation and the usage example inside `src/main.rs` for quick reading.

## Layout

```
rust/
├── Cargo.toml
├── creational/
│   └── factory_method/
├── structural/
│   └── adapter/
└── behavioral/
    └── observer/
```

## Commands

- `cargo run -p <pattern>` executes the binary once you add code.
- `cargo fmt` keeps formatting consistent across all crates.

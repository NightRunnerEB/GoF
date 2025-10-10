# Rust Workspace

Rust patterns live in a Cargo workspace. Each pattern has its own crate inside the `creational`, `structural`, or `behavioral` directories. Use `src/lib.rs` to keep both the pattern implementation and the client-side usage example together.

## Layout

```
rust/
├── Cargo.toml
├── creational/
│   └── factory_method/
├── structural/
│   └── adapter/
└── behavioral/
    ├── observer/
    ├── iterator/
    └── visitor/
```

## Commands

- `cargo test` compiles every pattern crate and runs inline tests.
- `cargo fmt` keeps formatting consistent across the workspace.

# Rust Workspace

Rust-примеры организованы как `Cargo` workspace c отдельным крейтом на каждый паттерн. Категории повторяют деление на `Creational`, `Structural`, `Behavioral`.

## Структура

```
rust/
├── Cargo.toml            # Настройка workspace
├── README.md             # Это описание
├── creational/
│   └── factory_method/   # Пример реализации паттерна "Фабричный метод"
├── structural/
│   └── adapter/          # Пример паттерна "Адаптер"
└── behavioral/
    └── observer/         # Пример паттерна "Наблюдатель"
```

Каждый крейт содержит:

- `src/lib.rs` — реализация паттерна;
- `examples/` — исполняемые примеры использования;
- `tests/` — интеграционные тесты (по желанию);
- `README.md` — пояснения и диаграммы.

## Команды

- `cargo test` — запустить тесты всех паттернов.
- `cargo run --example <name> --package <crate>` — запустить конкретный пример.

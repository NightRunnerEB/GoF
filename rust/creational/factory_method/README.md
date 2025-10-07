# Factory Method (Фабричный метод)

Кратко описывает идею паттерна с точки зрения Rust: выделяем интерфейс (trait) создания объектов и делегируем решение выбора конкретной реализации подклассам/функциям-фабрикам.

## Как запустить пример

```bash
cargo run --package factory_method --example dialog
```

## Тесты

```bash
cargo test --package factory_method
```

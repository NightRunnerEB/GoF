#!/usr/bin/env bash
set -euo pipefail

if [[ $# -ne 2 ]]; then
  echo "Usage: $0 <category> <pattern-slug>" >&2
  echo "Categories: creational | structural | behavioral" >&2
  exit 1
fi

category=$1
pattern_slug=$2
case "$category" in
  creational|structural|behavioral) ;;
  *)
    echo "Unknown category: $category" >&2
    exit 1
    ;;
esac

pattern_dir="rust/${category}/${pattern_slug}"
crate_name="${pattern_slug//-/_}"

if [[ -d "$pattern_dir" ]]; then
  echo "Pattern already exists at $pattern_dir" >&2
  exit 1
fi

mkdir -p "$pattern_dir/src" "$pattern_dir/examples" "$pattern_dir/tests"

cat <<TOML > "$pattern_dir/Cargo.toml"
[package]
name = "${crate_name}"
version = "0.1.0"
edition = "2021"
description = "GoF: ${pattern_slug//-/ } pattern in Rust"
license = "MIT"

[dependencies]
anyhow.workspace = true

[dev-dependencies]
pretty_assertions.workspace = true
TOML

cat <<RS > "$pattern_dir/src/lib.rs"
//! TODO: реализуйте паттерн "${pattern_slug//-/ }".

pub fn todo_demo() {
    unimplemented!("Add pattern implementation");
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    #[ignore]
    fn demo() {
        todo_demo();
    }
}
RS

cat <<MD > "$pattern_dir/README.md"
# ${pattern_slug//-/ } (Rust)

Добавьте описание и диаграммы для паттерна.

## Быстрый старт

```bash
cargo test --package ${crate_name}
```
MD

echo "Pattern scaffold created at $pattern_dir"
echo "Don't forget to add \"${category}/${pattern_slug}\" to the members array in rust/Cargo.toml"

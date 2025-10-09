# Iterator Pattern

## Problem Without The Pattern

We juggle different collections (arrays, lists, trees, custom data structures) and we want to **iterate over their elements**:

- traverse a list of users;
- find a product inside a category tree;
- walk through a two-dimensional matrix;
- replay the edit history in an editor.

The naive solution is that every collection **exposes its internal structure** and the client writes a bespoke loop for each case.

## Solution With Iterator

The pattern suggests we:

- extract a dedicated **Iterator** object that encapsulates traversal logic;
- add an `iterator()` method to the collection that returns this object;
- let the client work **only with the Iterator interface** (`hasNext()`, `next()` in Java or the `Iterator` trait in Rust) without caring about collection details.

---

## Benefits

- **Encapsulation.** Clients neither know nor depend on the internal structure of the collection.
- **Uniform interface.** Traversal looks the same for every collection.
- **Flexibility.** It is easy to swap traversal strategies (forward/backward, BFS/DFS, etc.).
- **Extensibility.** New collections arrive without changing client code—just provide their own `iterator()`.

---

## Simple Analogy

Imagine a library:

- Books may be stored alphabetically, by genre, or by author.
- Readers do not care **how** the warehouse is organized. They receive a **librarian (Iterator)** who hands out books one by one.
- When the storage order changes, the client code stays the same because the librarian still delivers books in the same way.

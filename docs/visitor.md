## Problem Without the Pattern

Sometimes we have a **hierarchy of different element types**, and we want to run multiple operations across them. Examples:

- In a document tree we have **Text, Image, Table**. Operations: print, export to HTML, calculate size.
- In a game we have **Monster, Player, NPC**. Operations: render, recalc health, save to disk.
- In a compiler we have **AST nodes** such as If, While, Expression. Operations: analyze, optimize, generate code.

### Naive Approach

```rust
// Trivial solution
trait Node {
    fn process(&self);
}

struct Text;
struct Image;

impl Node for Text {
    fn process(&self) {
        println!("Processing text");
    }
}

impl Node for Image {
    fn process(&self) {
        println!("Processing image");
    }
}
```

**What is wrong with this approach?**

- Each class has to embed the logic for every operation (print, export, measurement, etc.).
- Adding a new operation means touching every class (`Text`, `Image`, ...).
- Violates the **Open/Closed Principle**: the classes must be modified even though the underlying data structure stays the same.

---

## Solution With Visitor

The idea:

- Move each operation into a dedicated object — a Visitor.
- Every element in the hierarchy defines an `accept(visitor)` method.
- Inside `accept` the element calls the visitor method specific to its type (`visitText`, `visitImage`, ...).
- Adding a new operation is as simple as creating a new visitor **without touching the element classes**.

---

## What We Gain

- **Easy to add new operations.** The element hierarchy remains untouched.
- **Loose coupling.** Operation logic lives separately from the data.
- **Great for ASTs, GUIs, complex models.** Visitor shines when operations multiply.

---

## Simple Analogy

Picture a museum:

- The hall contains **Painting, Statue, Artifact** (elements).
- Different visitors arrive: **Art Historian** (analyzes style), **Photographer** (takes pictures), **Curator** (estimates value).
- Each object calls `accept(visitor)` and lets the visitor decide what to do.

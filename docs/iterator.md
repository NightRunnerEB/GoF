## Problem Without The Pattern

We often deal with multiple collection types (arrays, lists, trees, custom data structures) and want to **iterate through their elements**:

- walk a list of users;
- locate a product in a category tree;
- traverse a two-dimensional matrix;
- replay the history of actions in an editor.

The naive approach is to let every collection **expose its internal structure**, forcing client code to craft custom loops.

Example (Java):

```java
class Employee {
    String name;
    Employee(String name) { this.name = name; }
}

class Department {
    List<Employee> employees = new ArrayList<>();
    void addEmployee(Employee e) { employees.add(e); }
    List<Employee> getEmployees() { return employees; } // exposing internals!
}

class Company {
    List<Department> departments = new ArrayList<>();
    void addDepartment(Department d) { departments.add(d); }
    List<Department> getDepartments() { return departments; } // exposing internals!
}

public class Main {
    public static void main(String[] args) {
        Company company = new Company();
        Department it = new Department();
        it.addEmployee(new Employee("Alice"));
        it.addEmployee(new Employee("Bob"));
        company.addDepartment(it);

        for (Department d : company.getDepartments()) {
            for (Employee e : d.getEmployees()) {
                System.out.println(e.name);
            }
        }
    }
}
```

Example (Rust):

```rust
struct MyCollection {
    items: Vec<String>,
}
impl MyCollection {
    fn get_items(&self) -> &Vec<String> { &self.items }
}

for i in 0..collection.get_items().len() {
    println!("{}", collection.get_items()[i]);
}
```

### Why This Is Problematic

- **Encapsulation leaks.** Clients see the internal structure (`List`, `Vec`) and depend on it.
- **Different loops for different collections.** Arrays need index-based loops, linked lists need `while node.next`, trees rely on recursion. Clients must understand every data structure.
- **Implementation changes ripple outward.** Switch an array to a linked list and every client loop breaks.
- **Traversal strategy is rigid.** Want breadth-first instead of depth-first? Rewrite client code.

---

## Solution With Iterator

The pattern encourages us to:

- extract a dedicated **Iterator** object that encapsulates traversal logic;
- expose an `iterator()` method on the collection that returns this object;
- let the client interact **only with the Iterator interface** (`hasNext()`, `next()` in Java or the `Iterator` trait in Rust), staying unaware of collection details.

---

## What We Gain

- **Encapsulation.** Clients no longer depend on the collection’s internal layout.
- **Uniform interface.** Traversal code looks the same everywhere.
- **Flexibility.** Switching traversal strategies (forward/backward, BFS/DFS) is straightforward.
- **Extensibility.** New collections arrive without touching client code—just implement `iterator()`.

---

## Simple Analogy

Imagine a library:

- Books might be arranged alphabetically, by genre, or by author.
- Readers do not care **how** the storage is organized. They work with a **librarian (Iterator)** who hands out books sequentially.
- Reorganize the shelves however you like—the client code stays the same because the librarian still presents books through the same interface.

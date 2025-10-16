# Command

## Проблема без паттерна

We habe two objects:

- **sender** (the one who initiantes actions) — e.g. a remote controller, a UI button, an event handlers,
- **receiver** (the one who performs the actions) — a lamp, a document, a robot and others

If you write it directrly, the sender will be tightly bound to a receiver:

```java
button.onClick(() -> light.on());
```

or

```rust
remote.press_button(); // inside remote light.on() is called directly
```

What's bad here?

- **Tight coumpling:** the button knows about the lamp, the remote controller knows about the TV, etc.
- If we want the same button to controll the another object (e.g. a fan), we will have to change the button code.
- It is impossible to store actions in a queue, cancel or execute them later because of a call - is just a method.

---

## Solution with Command

The pattern suggests putting the method call into a separate object - **command**

A Command - is a "box" that stores:

- the reference to **receiver**,
- all the information needed for the call (arguments, parameters)
- the `execute()` method, which internally executes `receiver.some_action()`.

And the sender (Invoker):

- stores the **command**, not receiver;
- it does not care what is inside — it just calls `command.execute()`.

---

## What we gain

1. **Loose coipling.**

    The sender only knows about the `Command` interface. It is completely independ of receiver type.

2. **Universality**

    In the same Invoker you can "load" different commands: `LightOnCommand`, `LightOffCommand`, `FanStartCommand`, etc.
    The Invoker code doen not change

3. **History and undo**

    Since commands are objects, they can be

    - put in a list,
    - execute later,
    - logged,
    - undone via undo()
4. **Queue of the Commans**

    Commands can be added to the queue (for example, for async processing).

    It transforms request into **data** that can be managed.

---

## Simple analogy

Imagine that:

- **Invoker** — is the waiter,
- **Receiver** — is the cook,
- **Command** — is a order slip.

The waiter does not cook the food himself and he doesn't have to know what’s happening in the chef’s kitchen. He just hands the order slip(command) to the chef and can reissue or defer it whenever needed.

And the cook prepares what is written there.

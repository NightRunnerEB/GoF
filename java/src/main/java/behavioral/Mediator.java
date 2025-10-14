package behavioral;

import java.util.ArrayList;
import java.util.List;

interface Mediator {

    void sendMessage(String msg, User user);

    void addUser(User user);
}

class ChatMediator implements Mediator {

    private final List<User> users = new ArrayList<>();

    @Override
    public void addUser(User user) {
        users.add(user);
    }

    @Override
    public void sendMessage(String msg, User sender) {
        for (User u : users) {
            if (u != sender) {
                u.receive(msg);
            }
        }
    }
}

abstract class User {

    protected Mediator mediator;
    protected String name;

    public User(Mediator m, String name) {
        this.mediator = m;
        this.name = name;
    }

    public abstract void send(String msg);

    public abstract void receive(String msg);
}

class ConcreteUser extends User {

    public ConcreteUser(Mediator m, String name) {
        super(m, name);
    }

    @Override
    public void send(String msg) {
        System.out.println(name + " sends: " + msg);
        mediator.sendMessage(msg, this);
    }

    @Override
    public void receive(String msg) {
        System.out.println(name + " receives: " + msg);
    }
}

@SuppressWarnings("unused")
final class MediatorExample {

    private MediatorExample() {
    }

    public static void example() {
        ChatMediator mediator = new ChatMediator();

        User alice = new ConcreteUser(mediator, "Alice");
        User bob = new ConcreteUser(mediator, "Bob");
        User charlie = new ConcreteUser(mediator, "Charlie");

        mediator.addUser(alice);
        mediator.addUser(bob);
        mediator.addUser(charlie);

        alice.send("Hi everyone!");
        bob.send("Hello Alice!");
    }
}

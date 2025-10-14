package behavioral;

interface State {

    void insertCard(ATM atm);

    void enterPin(ATM atm, int pin);

    void withdraw(ATM atm);
}

class NoCardState implements State {

    @Override
    public void insertCard(ATM atm) {
        System.out.println("Card inserted");
        atm.setState(new HasCardState());
    }

    @Override
    public void enterPin(ATM atm, int pin) {
        System.out.println("Insert card first");
    }

    @Override
    public void withdraw(ATM atm) {
        System.out.println("Insert card first");
    }
}

class HasCardState implements State {

    @Override
    public void insertCard(ATM atm) {
        System.out.println("Card already inserted");
    }

    @Override
    public void enterPin(ATM atm, int pin) {
        if (pin == 1234) {
            System.out.println("PIN correct");
            atm.setState(new AuthenticatedState());
        } else {
            System.out.println("Wrong PIN");
        }
    }

    @Override
    public void withdraw(ATM atm) {
        System.out.println("Enter PIN first");
    }
}

class AuthenticatedState implements State {

    @Override
    public void insertCard(ATM atm) {
        System.out.println("Already authenticated");
    }

    @Override
    public void enterPin(ATM atm, int pin) {
        System.out.println("Already authenticated");
    }

    @Override
    public void withdraw(ATM atm) {
        System.out.println("Cash withdrawn");
        atm.setState(new NoCardState());
    }
}

class ATM {

    private State state = new NoCardState();

    void setState(State s) {
        this.state = s;
    }

    public void insertCard() {
        state.insertCard(this);
    }

    public void enterPin(int pin) {
        state.enterPin(this, pin);
    }

    public void withdraw() {
        state.withdraw(this);
    }
}

@SuppressWarnings("unused")
final class StateExample {

    private StateExample() {
    }

    public static void example() {
        ATM atm = new ATM();

        atm.withdraw();
        atm.insertCard();
        atm.enterPin(1111);
        atm.enterPin(1234);
        atm.withdraw();
    }
}

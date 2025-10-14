package behavioral;

interface PaymentStrategy {

    void pay(double amount);
}

class PayPalPayment implements PaymentStrategy {

    @Override
    public void pay(double amount) {
        System.out.println("Paid " + amount + " via PayPal");
    }
}

class CardPayment implements PaymentStrategy {

    @Override
    public void pay(double amount) {
        System.out.println("Paid " + amount + " via Credit Card");
    }
}

class CryptoPayment implements PaymentStrategy {

    @Override
    public void pay(double amount) {
        System.out.println("Paid " + amount + " via Crypto");
    }
}

// Context
class PaymentService {

    private PaymentStrategy strategy;

    public void setStrategy(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    public void checkout(double amount) {
        if (strategy == null) {
            throw new IllegalStateException("No payment strategy selected");
        }
        strategy.pay(amount);
    }
}

@SuppressWarnings("unused")
final class StrategyExample {

    private StrategyExample() {
    }

    public static void example() {
        PaymentService service = new PaymentService();

        service.setStrategy(new PayPalPayment());
        service.checkout(100);

        service.setStrategy(new CardPayment());
        service.checkout(200);

        service.setStrategy(new CryptoPayment());
        service.checkout(300);
    }
}

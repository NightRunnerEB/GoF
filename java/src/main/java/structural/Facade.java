package structural;

class Inventory {

    boolean reserve(String sku, int qty) {
        System.out.printf("Inventory: reserving %d x %s%n", qty, sku);
        return true;
    }
}

class Payment {

    void charge(String customerId, long amountCents) {
        System.out.printf("Payment: charge %s for %d cents%n", customerId, amountCents);
    }
}

class Shipping {

    String createLabel(String sku, int qty, String address) {
        System.out.printf("Shipping: label for %d x %s to %s%n", qty, sku, address);
        return "LBL-" + sku + "-" + qty;
    }
}

class Notifier {

    void email(String to, String subject, String body) {
        System.out.printf("Email -> %s: %s%n%s%n", to, subject, body);
    }
}

// Facade
class OrderFacade {

    private final Inventory inv = new Inventory();
    private final Payment pay = new Payment();
    private final Shipping ship = new Shipping();
    private final Notifier mail = new Notifier();

    public String placeOrder(String customerId, String email, String address,
            String sku, int qty, long priceCents) {
        if (!inv.reserve(sku, qty)) {
            throw new RuntimeException("Out of stock");
        }
        pay.charge(customerId, priceCents * qty);
        String label = ship.createLabel(sku, qty, address);
        mail.email(email, "Order confirmed",
                "Your order " + sku + " x " + qty + " is on the way. Label: " + label);
        return label;
    }
}

@SuppressWarnings("unused")
final class FacadeExample {

    private FacadeExample() {
    }

    public static void example() {
        OrderFacade facade = new OrderFacade();
        String label = facade.placeOrder("cust_42", "user@example.com", "Baker St. 221B",
                "SKU-CPU-7950X", 1, 499_00);
        System.out.println("Done. Shipping label: " + label);
    }
}

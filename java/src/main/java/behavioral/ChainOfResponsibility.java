package behavioral;

abstract class Handler {

    protected Handler next;

    public Handler setNext(Handler next) {
        this.next = next;
        return next;
    }

    public void handle(String request) {
        if (next != null) {
            next.handle(request);
        }
    }
}

// Concrete handlers
class AuthHandler extends Handler {

    @Override
    public void handle(String request) {
        if (request.contains("auth")) {
            System.out.println("AuthHandler: handled request");
        } else if (next != null) {
            next.handle(request);
        }
    }
}

class LoggerHandler extends Handler {

    @Override
    public void handle(String request) {
        System.out.println("Logger: " + request);
        if (next != null) {
            next.handle(request);
        }
    }
}

class BusinessHandler extends Handler {

    @Override
    public void handle(String request) {
        System.out.println("BusinessHandler: executing business logic");
    }
}

// Client
@SuppressWarnings("unused")
final class ChainOfResponsibilityExample {

    private ChainOfResponsibilityExample() {
    }

    public static void example() {
        Handler auth = new AuthHandler();
        Handler log = new LoggerHandler();
        Handler business = new BusinessHandler();
        auth.setNext(log).setNext(business);

        auth.handle("auth request");
        auth.handle("normal request");
    }
}

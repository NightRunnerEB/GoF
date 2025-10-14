package structural;

// Expected interface
interface Logger {

    void info(String msg);
}

// Legacy library
class OldLogger {

    public void write(String text) {
        System.out.println("[OLD LOG] " + text);
    }
}

// Adapter — maps the old API onto the required interface
class LoggerAdapter implements Logger {

    private final OldLogger old;

    public LoggerAdapter(OldLogger old) {
        this.old = old;
    }

    @Override
    public void info(String msg) {
        old.write("INFO: " + msg); // convert to legacy format
    }
}

@SuppressWarnings("unused")
final class AdapterExample {

    private AdapterExample() {
    }

    public static void example() {
        Logger logger = new LoggerAdapter(new OldLogger());
        logger.info("Started"); // Client never touches OldLogger directly
    }
}

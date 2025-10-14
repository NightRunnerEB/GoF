package creational;

class Logger {

    private static Logger instance; // single instance

    private Logger() {
    } // disallow new from outside

    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger(); // lazily create
        }
        return instance;
    }

    public void log(String msg) {
        System.out.println("[LOG] " + msg);
    }
}

@SuppressWarnings("unused")
final class SingletonExample {

    private SingletonExample() {
    }

    public static void example() {
        Logger.getInstance().log("Start");
        Logger.getInstance().log("Continue");
    }
}
